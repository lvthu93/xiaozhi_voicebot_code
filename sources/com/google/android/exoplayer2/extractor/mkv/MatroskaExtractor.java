package com.google.android.exoplayer2.extractor.mkv;

import android.util.SparseArray;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Token;

public class MatroskaExtractor implements Extractor {
    public static final byte[] bb = {49, 10, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 32, 45, 45, 62, 32, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 10};
    public static final byte[] bc = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    public static final byte[] bd = {68, 105, 97, 108, 111, 103, 117, 101, 58, 32, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44};
    public static final UUID be = new UUID(72057594037932032L, -9223371306706625679L);
    public static final Map<String, Integer> bf;
    public final z1 a;
    public long aa;
    public long ab;
    @Nullable
    public LongArray ac;
    @Nullable
    public LongArray ad;
    public boolean ae;
    public boolean af;
    public int ag;
    public long ah;
    public long ai;
    public int aj;
    public int ak;
    public int[] al;
    public int am;
    public int an;
    public int ao;
    public int ap;
    public boolean aq;
    public int ar;
    public int as;
    public int at;
    public boolean au;
    public boolean av;
    public boolean aw;
    public int ax;
    public byte ay;
    public boolean az;
    public final ld b;
    public ExtractorOutput ba;
    public final SparseArray<b> c;
    public final boolean d;
    public final ParsableByteArray e;
    public final ParsableByteArray f;
    public final ParsableByteArray g;
    public final ParsableByteArray h;
    public final ParsableByteArray i;
    public final ParsableByteArray j;
    public final ParsableByteArray k;
    public final ParsableByteArray l;
    public final ParsableByteArray m;
    public final ParsableByteArray n;
    public ByteBuffer o;
    public long p;
    public long q;
    public long r;
    public long s;
    public long t;
    @Nullable
    public b u;
    public boolean v;
    public int w;
    public long x;
    public boolean y;
    public long z;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public final class a implements EbmlProcessor {
        public a() {
        }

        public void binaryElement(int i, int i2, ExtractorInput extractorInput) throws IOException {
            int i3;
            long j;
            int i4;
            byte b;
            int i5;
            int i6 = i;
            int i7 = i2;
            ExtractorInput extractorInput2 = extractorInput;
            MatroskaExtractor matroskaExtractor = MatroskaExtractor.this;
            SparseArray<b> sparseArray = matroskaExtractor.c;
            int i8 = 4;
            int i9 = 0;
            int i10 = 1;
            if (i6 == 161 || i6 == 163) {
                int i11 = matroskaExtractor.ag;
                ParsableByteArray parsableByteArray = matroskaExtractor.g;
                if (i11 == 0) {
                    ld ldVar = matroskaExtractor.b;
                    matroskaExtractor.am = (int) ldVar.readUnsignedVarint(extractorInput2, false, true, 8);
                    matroskaExtractor.an = ldVar.getLastLength();
                    matroskaExtractor.ai = -9223372036854775807L;
                    matroskaExtractor.ag = 1;
                    parsableByteArray.reset(0);
                }
                b bVar = sparseArray.get(matroskaExtractor.am);
                if (bVar == null) {
                    extractorInput2.skipFully(i7 - matroskaExtractor.an);
                    matroskaExtractor.ag = 0;
                    return;
                }
                Assertions.checkNotNull(bVar.ax);
                if (matroskaExtractor.ag == 1) {
                    matroskaExtractor.e(extractorInput2, 3);
                    int i12 = (parsableByteArray.getData()[2] & 6) >> 1;
                    if (i12 == 0) {
                        matroskaExtractor.ak = 1;
                        int[] iArr = matroskaExtractor.al;
                        if (iArr == null) {
                            iArr = new int[1];
                        } else if (iArr.length < 1) {
                            iArr = new int[Math.max(iArr.length * 2, 1)];
                        }
                        matroskaExtractor.al = iArr;
                        iArr[0] = (i7 - matroskaExtractor.an) - 3;
                    } else {
                        matroskaExtractor.e(extractorInput2, 4);
                        int i13 = (parsableByteArray.getData()[3] & 255) + 1;
                        matroskaExtractor.ak = i13;
                        int[] iArr2 = matroskaExtractor.al;
                        if (iArr2 == null) {
                            iArr2 = new int[i13];
                        } else if (iArr2.length < i13) {
                            iArr2 = new int[Math.max(iArr2.length * 2, i13)];
                        }
                        matroskaExtractor.al = iArr2;
                        if (i12 == 2) {
                            int i14 = matroskaExtractor.ak;
                            Arrays.fill(iArr2, 0, i14, ((i7 - matroskaExtractor.an) - 4) / i14);
                        } else if (i12 == 1) {
                            int i15 = 0;
                            int i16 = 0;
                            while (true) {
                                i4 = matroskaExtractor.ak;
                                if (i15 >= i4 - 1) {
                                    break;
                                }
                                matroskaExtractor.al[i15] = 0;
                                do {
                                    i8++;
                                    matroskaExtractor.e(extractorInput2, i8);
                                    b = parsableByteArray.getData()[i8 - 1] & 255;
                                    int[] iArr3 = matroskaExtractor.al;
                                    i5 = iArr3[i15] + b;
                                    iArr3[i15] = i5;
                                } while (b == 255);
                                i16 += i5;
                                i15++;
                            }
                            matroskaExtractor.al[i4 - 1] = ((i7 - matroskaExtractor.an) - i8) - i16;
                        } else if (i12 == 3) {
                            int i17 = 0;
                            int i18 = 0;
                            while (true) {
                                int i19 = matroskaExtractor.ak;
                                if (i17 >= i19 - 1) {
                                    matroskaExtractor.al[i19 - 1] = ((i7 - matroskaExtractor.an) - i8) - i18;
                                    break;
                                }
                                matroskaExtractor.al[i17] = i9;
                                i8++;
                                matroskaExtractor.e(extractorInput2, i8);
                                int i20 = i8 - 1;
                                if (parsableByteArray.getData()[i20] != 0) {
                                    int i21 = 8;
                                    int i22 = 0;
                                    while (true) {
                                        if (i22 >= i21) {
                                            j = 0;
                                            break;
                                        }
                                        int i23 = i10 << (7 - i22);
                                        if ((parsableByteArray.getData()[i20] & i23) != 0) {
                                            int i24 = i8 + i22;
                                            matroskaExtractor.e(extractorInput2, i24);
                                            j = (long) ((~i23) & parsableByteArray.getData()[i20] & 255);
                                            for (int i25 = i20 + 1; i25 < i24; i25++) {
                                                j = (j << 8) | ((long) (parsableByteArray.getData()[i25] & 255));
                                                i24 = i24;
                                            }
                                            int i26 = i24;
                                            if (i17 > 0) {
                                                j -= (1 << ((i22 * 7) + 6)) - 1;
                                            }
                                            i8 = i26;
                                        } else {
                                            i22++;
                                            i21 = 8;
                                            i10 = 1;
                                        }
                                    }
                                    if (j >= -2147483648L && j <= 2147483647L) {
                                        int i27 = (int) j;
                                        int[] iArr4 = matroskaExtractor.al;
                                        if (i17 != 0) {
                                            i27 += iArr4[i17 - 1];
                                        }
                                        iArr4[i17] = i27;
                                        i18 += i27;
                                        i17++;
                                        i9 = 0;
                                        i10 = 1;
                                    }
                                } else {
                                    throw new ParserException("No valid varint length mask found");
                                }
                            }
                            throw new ParserException("EBML lacing sample size out of range.");
                        } else {
                            throw new ParserException(y2.d(36, "Unexpected lacing value: ", i12));
                        }
                    }
                    matroskaExtractor.ah = matroskaExtractor.g((long) ((parsableByteArray.getData()[1] & 255) | (parsableByteArray.getData()[0] << 8))) + matroskaExtractor.ab;
                    if (bVar.d == 2 || (i6 == 163 && (parsableByteArray.getData()[2] & 128) == 128)) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    matroskaExtractor.ao = i3;
                    matroskaExtractor.ag = 2;
                    matroskaExtractor.aj = 0;
                }
                if (i6 == 163) {
                    while (true) {
                        int i28 = matroskaExtractor.aj;
                        if (i28 < matroskaExtractor.ak) {
                            matroskaExtractor.c(bVar, ((long) ((matroskaExtractor.aj * bVar.e) / 1000)) + matroskaExtractor.ah, matroskaExtractor.ao, matroskaExtractor.h(matroskaExtractor.al[i28], extractorInput2, bVar), 0);
                            matroskaExtractor.aj++;
                        } else {
                            matroskaExtractor.ag = 0;
                            return;
                        }
                    }
                } else {
                    while (true) {
                        int i29 = matroskaExtractor.aj;
                        if (i29 < matroskaExtractor.ak) {
                            int[] iArr5 = matroskaExtractor.al;
                            iArr5[i29] = matroskaExtractor.h(iArr5[i29], extractorInput2, bVar);
                            matroskaExtractor.aj++;
                        } else {
                            return;
                        }
                    }
                }
            } else if (i6 != 165) {
                if (i6 == 16877) {
                    matroskaExtractor.b(i6);
                    b bVar2 = matroskaExtractor.u;
                    int i30 = bVar2.g;
                    if (i30 == 1685485123 || i30 == 1685480259) {
                        byte[] bArr = new byte[i7];
                        bVar2.an = bArr;
                        extractorInput2.readFully(bArr, 0, i7);
                        return;
                    }
                    extractorInput2.skipFully(i7);
                } else if (i6 == 16981) {
                    matroskaExtractor.b(i6);
                    byte[] bArr2 = new byte[i7];
                    matroskaExtractor.u.i = bArr2;
                    extractorInput2.readFully(bArr2, 0, i7);
                } else if (i6 == 18402) {
                    byte[] bArr3 = new byte[i7];
                    extractorInput2.readFully(bArr3, 0, i7);
                    matroskaExtractor.b(i6);
                    matroskaExtractor.u.j = new TrackOutput.CryptoData(1, bArr3, 0, 0);
                } else if (i6 == 21419) {
                    ParsableByteArray parsableByteArray2 = matroskaExtractor.i;
                    Arrays.fill(parsableByteArray2.getData(), (byte) 0);
                    extractorInput2.readFully(parsableByteArray2.getData(), 4 - i7, i7);
                    parsableByteArray2.setPosition(0);
                    matroskaExtractor.w = (int) parsableByteArray2.readUnsignedInt();
                } else if (i6 == 25506) {
                    matroskaExtractor.b(i6);
                    byte[] bArr4 = new byte[i7];
                    matroskaExtractor.u.k = bArr4;
                    extractorInput2.readFully(bArr4, 0, i7);
                } else if (i6 == 30322) {
                    matroskaExtractor.b(i6);
                    byte[] bArr5 = new byte[i7];
                    matroskaExtractor.u.v = bArr5;
                    extractorInput2.readFully(bArr5, 0, i7);
                } else {
                    throw new ParserException(y2.d(26, "Unexpected id: ", i6));
                }
            } else if (matroskaExtractor.ag == 2) {
                b bVar3 = sparseArray.get(matroskaExtractor.am);
                if (matroskaExtractor.ap != 4 || !"V_VP9".equals(bVar3.b)) {
                    extractorInput2.skipFully(i7);
                    return;
                }
                ParsableByteArray parsableByteArray3 = matroskaExtractor.n;
                parsableByteArray3.reset(i7);
                extractorInput2.readFully(parsableByteArray3.getData(), 0, i7);
            }
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:166:0x0334, code lost:
            if (r7.equals("A_MS/ACM") == false) goto L_0x034d;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void endMasterElement(int r20) throws com.google.android.exoplayer2.ParserException {
            /*
                r19 = this;
                r0 = r20
                r1 = r19
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor r9 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.this
                com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r9.ba
                com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r2)
                android.util.SparseArray<com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b> r2 = r9.c
                r3 = 160(0xa0, float:2.24E-43)
                r4 = 2
                if (r0 == r3) goto L_0x036e
                r3 = 174(0xae, float:2.44E-43)
                r6 = -1
                if (r0 == r3) goto L_0x0191
                r3 = 19899(0x4dbb, float:2.7884E-41)
                r11 = -1
                r4 = 475249515(0x1c53bb6b, float:7.0056276E-22)
                if (r0 == r3) goto L_0x0178
                r3 = 25152(0x6240, float:3.5245E-41)
                if (r0 == r3) goto L_0x0149
                r3 = 28032(0x6d80, float:3.9281E-41)
                if (r0 == r3) goto L_0x0132
                r3 = 357149030(0x1549a966, float:4.072526E-26)
                r13 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
                if (r0 == r3) goto L_0x0119
                r3 = 374648427(0x1654ae6b, float:1.718026E-25)
                if (r0 == r3) goto L_0x0104
                if (r0 == r4) goto L_0x003b
                goto L_0x03bb
            L_0x003b:
                boolean r0 = r9.v
                if (r0 != 0) goto L_0x00fd
                com.google.android.exoplayer2.extractor.ExtractorOutput r0 = r9.ba
                com.google.android.exoplayer2.util.LongArray r2 = r9.ac
                com.google.android.exoplayer2.util.LongArray r3 = r9.ad
                long r7 = r9.q
                int r4 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
                if (r4 == 0) goto L_0x00f0
                long r7 = r9.t
                int r4 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
                if (r4 == 0) goto L_0x00f0
                if (r2 == 0) goto L_0x00f0
                int r4 = r2.size()
                if (r4 == 0) goto L_0x00f0
                if (r3 == 0) goto L_0x00f0
                int r4 = r3.size()
                int r7 = r2.size()
                if (r4 == r7) goto L_0x0067
                goto L_0x00f0
            L_0x0067:
                int r4 = r2.size()
                int[] r7 = new int[r4]
                long[] r8 = new long[r4]
                long[] r11 = new long[r4]
                long[] r12 = new long[r4]
                r13 = 0
            L_0x0074:
                if (r13 >= r4) goto L_0x008b
                long r15 = r2.get(r13)
                r12[r13] = r15
                r14 = r11
                long r10 = r9.q
                long r16 = r3.get(r13)
                long r16 = r16 + r10
                r8[r13] = r16
                int r13 = r13 + 1
                r11 = r14
                goto L_0x0074
            L_0x008b:
                r14 = r11
                r10 = 0
            L_0x008d:
                int r2 = r4 + -1
                if (r10 >= r2) goto L_0x00a6
                int r2 = r10 + 1
                r15 = r8[r2]
                r17 = r8[r10]
                long r5 = r15 - r17
                int r6 = (int) r5
                r7[r10] = r6
                r5 = r12[r2]
                r15 = r12[r10]
                long r5 = r5 - r15
                r14[r10] = r5
                r10 = r2
                r6 = -1
                goto L_0x008d
            L_0x00a6:
                long r4 = r9.q
                long r10 = r9.p
                long r4 = r4 + r10
                r10 = r8[r2]
                long r4 = r4 - r10
                int r5 = (int) r4
                r7[r2] = r5
                long r4 = r9.t
                r10 = r12[r2]
                long r4 = r4 - r10
                r14[r2] = r4
                r10 = 0
                int r6 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
                if (r6 > 0) goto L_0x00e8
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r10 = 72
                r6.<init>(r10)
                java.lang.String r10 = "Discarding last cue point with unexpected duration: "
                r6.append(r10)
                r6.append(r4)
                java.lang.String r4 = r6.toString()
                java.lang.String r5 = "MatroskaExtractor"
                com.google.android.exoplayer2.util.Log.w(r5, r4)
                int[] r7 = java.util.Arrays.copyOf(r7, r2)
                long[] r8 = java.util.Arrays.copyOf(r8, r2)
                r4 = r14
                long[] r11 = java.util.Arrays.copyOf(r4, r2)
                long[] r12 = java.util.Arrays.copyOf(r12, r2)
                goto L_0x00ea
            L_0x00e8:
                r4 = r14
                r11 = r4
            L_0x00ea:
                com.google.android.exoplayer2.extractor.ChunkIndex r2 = new com.google.android.exoplayer2.extractor.ChunkIndex
                r2.<init>(r7, r8, r11, r12)
                goto L_0x00f7
            L_0x00f0:
                com.google.android.exoplayer2.extractor.SeekMap$Unseekable r2 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
                long r4 = r9.t
                r2.<init>(r4)
            L_0x00f7:
                r0.seekMap(r2)
                r0 = 1
                r9.v = r0
            L_0x00fd:
                r0 = 0
                r9.ac = r0
                r9.ad = r0
                goto L_0x03bb
            L_0x0104:
                int r0 = r2.size()
                if (r0 == 0) goto L_0x0111
                com.google.android.exoplayer2.extractor.ExtractorOutput r0 = r9.ba
                r0.endTracks()
                goto L_0x03bb
            L_0x0111:
                com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "No valid tracks were found"
                r0.<init>((java.lang.String) r2)
                throw r0
            L_0x0119:
                long r2 = r9.r
                int r0 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
                if (r0 != 0) goto L_0x0124
                r2 = 1000000(0xf4240, double:4.940656E-318)
                r9.r = r2
            L_0x0124:
                long r2 = r9.s
                int r0 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
                if (r0 == 0) goto L_0x03bb
                long r2 = r9.g(r2)
                r9.t = r2
                goto L_0x03bb
            L_0x0132:
                r9.b(r0)
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r0 = r9.u
                boolean r2 = r0.h
                if (r2 == 0) goto L_0x03bb
                byte[] r0 = r0.i
                if (r0 != 0) goto L_0x0141
                goto L_0x03bb
            L_0x0141:
                com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Combining encryption and compression is not supported"
                r0.<init>((java.lang.String) r2)
                throw r0
            L_0x0149:
                r9.b(r0)
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r0 = r9.u
                boolean r2 = r0.h
                if (r2 == 0) goto L_0x03bb
                com.google.android.exoplayer2.extractor.TrackOutput$CryptoData r2 = r0.j
                if (r2 == 0) goto L_0x0170
                com.google.android.exoplayer2.drm.DrmInitData r3 = new com.google.android.exoplayer2.drm.DrmInitData
                r5 = 1
                com.google.android.exoplayer2.drm.DrmInitData$SchemeData[] r4 = new com.google.android.exoplayer2.drm.DrmInitData.SchemeData[r5]
                com.google.android.exoplayer2.drm.DrmInitData$SchemeData r5 = new com.google.android.exoplayer2.drm.DrmInitData$SchemeData
                java.util.UUID r6 = com.google.android.exoplayer2.C.a
                java.lang.String r7 = "video/webm"
                byte[] r2 = r2.b
                r5.<init>(r6, r7, r2)
                r2 = 0
                r4[r2] = r5
                r3.<init>((com.google.android.exoplayer2.drm.DrmInitData.SchemeData[]) r4)
                r0.l = r3
                goto L_0x03bb
            L_0x0170:
                com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Encrypted Track found but ContentEncKeyID was not found"
                r0.<init>((java.lang.String) r2)
                throw r0
            L_0x0178:
                int r0 = r9.w
                r6 = -1
                if (r0 == r6) goto L_0x0189
                long r2 = r9.x
                int r5 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
                if (r5 == 0) goto L_0x0189
                if (r0 != r4) goto L_0x03bb
                r9.z = r2
                goto L_0x03bb
            L_0x0189:
                com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Mandatory element SeekID or SeekPosition not found"
                r0.<init>((java.lang.String) r2)
                throw r0
            L_0x0191:
                r5 = 1
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r0 = r9.u
                java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r0)
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r0 = (com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.b) r0
                java.lang.String r7 = r0.b
                if (r7 == 0) goto L_0x0366
                int r8 = r7.hashCode()
                switch(r8) {
                    case -2095576542: goto L_0x0342;
                    case -2095575984: goto L_0x0337;
                    case -1985379776: goto L_0x032e;
                    case -1784763192: goto L_0x0323;
                    case -1730367663: goto L_0x0318;
                    case -1482641358: goto L_0x030d;
                    case -1482641357: goto L_0x0302;
                    case -1373388978: goto L_0x02f7;
                    case -933872740: goto L_0x02e9;
                    case -538363189: goto L_0x02db;
                    case -538363109: goto L_0x02cd;
                    case -425012669: goto L_0x02bf;
                    case -356037306: goto L_0x02b1;
                    case 62923557: goto L_0x02a3;
                    case 62923603: goto L_0x0295;
                    case 62927045: goto L_0x0287;
                    case 82318131: goto L_0x0279;
                    case 82338133: goto L_0x026b;
                    case 82338134: goto L_0x025d;
                    case 99146302: goto L_0x024f;
                    case 444813526: goto L_0x0241;
                    case 542569478: goto L_0x0233;
                    case 635596514: goto L_0x0225;
                    case 725948237: goto L_0x0217;
                    case 725957860: goto L_0x0209;
                    case 738597099: goto L_0x01fb;
                    case 855502857: goto L_0x01ed;
                    case 1422270023: goto L_0x01df;
                    case 1809237540: goto L_0x01d1;
                    case 1950749482: goto L_0x01c3;
                    case 1950789798: goto L_0x01b5;
                    case 1951062397: goto L_0x01a7;
                    default: goto L_0x01a5;
                }
            L_0x01a5:
                goto L_0x034d
            L_0x01a7:
                java.lang.String r4 = "A_OPUS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01b1
                goto L_0x034d
            L_0x01b1:
                r4 = 31
                goto L_0x034e
            L_0x01b5:
                java.lang.String r4 = "A_FLAC"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01bf
                goto L_0x034d
            L_0x01bf:
                r4 = 30
                goto L_0x034e
            L_0x01c3:
                java.lang.String r4 = "A_EAC3"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01cd
                goto L_0x034d
            L_0x01cd:
                r4 = 29
                goto L_0x034e
            L_0x01d1:
                java.lang.String r4 = "V_MPEG2"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01db
                goto L_0x034d
            L_0x01db:
                r4 = 28
                goto L_0x034e
            L_0x01df:
                java.lang.String r4 = "S_TEXT/UTF8"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01e9
                goto L_0x034d
            L_0x01e9:
                r4 = 27
                goto L_0x034e
            L_0x01ed:
                java.lang.String r4 = "V_MPEGH/ISO/HEVC"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x01f7
                goto L_0x034d
            L_0x01f7:
                r4 = 26
                goto L_0x034e
            L_0x01fb:
                java.lang.String r4 = "S_TEXT/ASS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0205
                goto L_0x034d
            L_0x0205:
                r4 = 25
                goto L_0x034e
            L_0x0209:
                java.lang.String r4 = "A_PCM/INT/LIT"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0213
                goto L_0x034d
            L_0x0213:
                r4 = 24
                goto L_0x034e
            L_0x0217:
                java.lang.String r4 = "A_PCM/INT/BIG"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0221
                goto L_0x034d
            L_0x0221:
                r4 = 23
                goto L_0x034e
            L_0x0225:
                java.lang.String r4 = "A_PCM/FLOAT/IEEE"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x022f
                goto L_0x034d
            L_0x022f:
                r4 = 22
                goto L_0x034e
            L_0x0233:
                java.lang.String r4 = "A_DTS/EXPRESS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x023d
                goto L_0x034d
            L_0x023d:
                r4 = 21
                goto L_0x034e
            L_0x0241:
                java.lang.String r4 = "V_THEORA"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x024b
                goto L_0x034d
            L_0x024b:
                r4 = 20
                goto L_0x034e
            L_0x024f:
                java.lang.String r4 = "S_HDMV/PGS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0259
                goto L_0x034d
            L_0x0259:
                r4 = 19
                goto L_0x034e
            L_0x025d:
                java.lang.String r4 = "V_VP9"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0267
                goto L_0x034d
            L_0x0267:
                r4 = 18
                goto L_0x034e
            L_0x026b:
                java.lang.String r4 = "V_VP8"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0275
                goto L_0x034d
            L_0x0275:
                r4 = 17
                goto L_0x034e
            L_0x0279:
                java.lang.String r4 = "V_AV1"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0283
                goto L_0x034d
            L_0x0283:
                r4 = 16
                goto L_0x034e
            L_0x0287:
                java.lang.String r4 = "A_DTS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0291
                goto L_0x034d
            L_0x0291:
                r4 = 15
                goto L_0x034e
            L_0x0295:
                java.lang.String r4 = "A_AC3"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x029f
                goto L_0x034d
            L_0x029f:
                r4 = 14
                goto L_0x034e
            L_0x02a3:
                java.lang.String r4 = "A_AAC"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02ad
                goto L_0x034d
            L_0x02ad:
                r4 = 13
                goto L_0x034e
            L_0x02b1:
                java.lang.String r4 = "A_DTS/LOSSLESS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02bb
                goto L_0x034d
            L_0x02bb:
                r4 = 12
                goto L_0x034e
            L_0x02bf:
                java.lang.String r4 = "S_VOBSUB"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02c9
                goto L_0x034d
            L_0x02c9:
                r4 = 11
                goto L_0x034e
            L_0x02cd:
                java.lang.String r4 = "V_MPEG4/ISO/AVC"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02d7
                goto L_0x034d
            L_0x02d7:
                r4 = 10
                goto L_0x034e
            L_0x02db:
                java.lang.String r4 = "V_MPEG4/ISO/ASP"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02e5
                goto L_0x034d
            L_0x02e5:
                r4 = 9
                goto L_0x034e
            L_0x02e9:
                java.lang.String r4 = "S_DVBSUB"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x02f3
                goto L_0x034d
            L_0x02f3:
                r4 = 8
                goto L_0x034e
            L_0x02f7:
                java.lang.String r4 = "V_MS/VFW/FOURCC"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0300
                goto L_0x034d
            L_0x0300:
                r4 = 7
                goto L_0x034e
            L_0x0302:
                java.lang.String r4 = "A_MPEG/L3"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x030b
                goto L_0x034d
            L_0x030b:
                r4 = 6
                goto L_0x034e
            L_0x030d:
                java.lang.String r4 = "A_MPEG/L2"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0316
                goto L_0x034d
            L_0x0316:
                r4 = 5
                goto L_0x034e
            L_0x0318:
                java.lang.String r4 = "A_VORBIS"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0321
                goto L_0x034d
            L_0x0321:
                r4 = 4
                goto L_0x034e
            L_0x0323:
                java.lang.String r4 = "A_TRUEHD"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x032c
                goto L_0x034d
            L_0x032c:
                r4 = 3
                goto L_0x034e
            L_0x032e:
                java.lang.String r8 = "A_MS/ACM"
                boolean r7 = r7.equals(r8)
                if (r7 != 0) goto L_0x034e
                goto L_0x034d
            L_0x0337:
                java.lang.String r4 = "V_MPEG4/ISO/SP"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x0340
                goto L_0x034d
            L_0x0340:
                r4 = 1
                goto L_0x034e
            L_0x0342:
                java.lang.String r4 = "V_MPEG4/ISO/AP"
                boolean r4 = r7.equals(r4)
                if (r4 != 0) goto L_0x034b
                goto L_0x034d
            L_0x034b:
                r4 = 0
                goto L_0x034e
            L_0x034d:
                r4 = -1
            L_0x034e:
                switch(r4) {
                    case 0: goto L_0x0353;
                    case 1: goto L_0x0353;
                    case 2: goto L_0x0353;
                    case 3: goto L_0x0353;
                    case 4: goto L_0x0353;
                    case 5: goto L_0x0353;
                    case 6: goto L_0x0353;
                    case 7: goto L_0x0353;
                    case 8: goto L_0x0353;
                    case 9: goto L_0x0353;
                    case 10: goto L_0x0353;
                    case 11: goto L_0x0353;
                    case 12: goto L_0x0353;
                    case 13: goto L_0x0353;
                    case 14: goto L_0x0353;
                    case 15: goto L_0x0353;
                    case 16: goto L_0x0353;
                    case 17: goto L_0x0353;
                    case 18: goto L_0x0353;
                    case 19: goto L_0x0353;
                    case 20: goto L_0x0353;
                    case 21: goto L_0x0353;
                    case 22: goto L_0x0353;
                    case 23: goto L_0x0353;
                    case 24: goto L_0x0353;
                    case 25: goto L_0x0353;
                    case 26: goto L_0x0353;
                    case 27: goto L_0x0353;
                    case 28: goto L_0x0353;
                    case 29: goto L_0x0353;
                    case 30: goto L_0x0353;
                    case 31: goto L_0x0353;
                    default: goto L_0x0351;
                }
            L_0x0351:
                r10 = 0
                goto L_0x0354
            L_0x0353:
                r10 = 1
            L_0x0354:
                if (r10 == 0) goto L_0x0362
                com.google.android.exoplayer2.extractor.ExtractorOutput r4 = r9.ba
                int r5 = r0.c
                r0.initializeOutput(r4, r5)
                int r4 = r0.c
                r2.put(r4, r0)
            L_0x0362:
                r0 = 0
                r9.u = r0
                goto L_0x03bb
            L_0x0366:
                com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "CodecId is missing in TrackEntry element"
                r0.<init>((java.lang.String) r2)
                throw r0
            L_0x036e:
                int r0 = r9.ag
                if (r0 == r4) goto L_0x0373
                goto L_0x03bb
            L_0x0373:
                r0 = 0
                r3 = 0
            L_0x0375:
                int r4 = r9.ak
                if (r0 >= r4) goto L_0x0381
                int[] r4 = r9.al
                r4 = r4[r0]
                int r3 = r3 + r4
                int r0 = r0 + 1
                goto L_0x0375
            L_0x0381:
                int r0 = r9.am
                java.lang.Object r0 = r2.get(r0)
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r0 = (com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.b) r0
                com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.ax
                com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
                r10 = 0
            L_0x038f:
                int r2 = r9.ak
                if (r10 >= r2) goto L_0x03b8
                long r4 = r9.ah
                int r2 = r0.e
                int r2 = r2 * r10
                int r2 = r2 / 1000
                long r6 = (long) r2
                long r4 = r4 + r6
                int r2 = r9.ao
                if (r10 != 0) goto L_0x03a7
                boolean r6 = r9.aq
                if (r6 != 0) goto L_0x03a7
                r2 = r2 | 1
            L_0x03a7:
                r6 = r2
                int[] r2 = r9.al
                r7 = r2[r10]
                int r11 = r3 - r7
                r2 = r9
                r3 = r0
                r8 = r11
                r2.c(r3, r4, r6, r7, r8)
                int r10 = r10 + 1
                r3 = r11
                goto L_0x038f
            L_0x03b8:
                r2 = 0
                r9.ag = r2
            L_0x03bb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.a.endMasterElement(int):void");
        }

        public void floatElement(int i, double d) throws ParserException {
            MatroskaExtractor matroskaExtractor = MatroskaExtractor.this;
            if (i == 181) {
                matroskaExtractor.b(i);
                matroskaExtractor.u.aq = (int) d;
            } else if (i != 17545) {
                switch (i) {
                    case 21969:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ad = (float) d;
                        return;
                    case 21970:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ae = (float) d;
                        return;
                    case 21971:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.af = (float) d;
                        return;
                    case 21972:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ag = (float) d;
                        return;
                    case 21973:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ah = (float) d;
                        return;
                    case 21974:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ai = (float) d;
                        return;
                    case 21975:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.aj = (float) d;
                        return;
                    case 21976:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.ak = (float) d;
                        return;
                    case 21977:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.al = (float) d;
                        return;
                    case 21978:
                        matroskaExtractor.b(i);
                        matroskaExtractor.u.am = (float) d;
                        return;
                    default:
                        switch (i) {
                            case 30323:
                                matroskaExtractor.b(i);
                                matroskaExtractor.u.s = (float) d;
                                return;
                            case 30324:
                                matroskaExtractor.b(i);
                                matroskaExtractor.u.t = (float) d;
                                return;
                            case 30325:
                                matroskaExtractor.b(i);
                                matroskaExtractor.u.u = (float) d;
                                return;
                            default:
                                matroskaExtractor.getClass();
                                return;
                        }
                }
            } else {
                matroskaExtractor.s = (long) d;
            }
        }

        public int getElementType(int i) {
            MatroskaExtractor.this.getClass();
            switch (i) {
                case Token.LABEL /*131*/:
                case Token.JSR /*136*/:
                case Token.CONST /*155*/:
                case Token.LETEXPR /*159*/:
                case 176:
                case 179:
                case 186:
                case 215:
                case 231:
                case 238:
                case 241:
                case 251:
                case 16871:
                case 16980:
                case 17029:
                case 17143:
                case 18401:
                case 18408:
                case 20529:
                case 20530:
                case 21420:
                case 21432:
                case 21680:
                case 21682:
                case 21690:
                case 21930:
                case 21945:
                case 21946:
                case 21947:
                case 21948:
                case 21949:
                case 21998:
                case 22186:
                case 22203:
                case 25188:
                case 30321:
                case 2352003:
                case 2807729:
                    return 2;
                case Token.EXPR_VOID /*134*/:
                case 17026:
                case 21358:
                case 2274716:
                    return 3;
                case 160:
                case Token.YIELD_STAR /*166*/:
                case 174:
                case 183:
                case 187:
                case 224:
                case 225:
                case 16868:
                case 18407:
                case 19899:
                case 20532:
                case 20533:
                case 21936:
                case 21968:
                case 25152:
                case 28032:
                case 30113:
                case 30320:
                case 290298740:
                case 357149030:
                case 374648427:
                case 408125543:
                case 440786851:
                case 475249515:
                case 524531317:
                    return 1;
                case Token.DEBUGGER /*161*/:
                case Token.GENEXPR /*163*/:
                case Token.ARROW /*165*/:
                case 16877:
                case 16981:
                case 18402:
                case 21419:
                case 25506:
                case 30322:
                    return 4;
                case 181:
                case 17545:
                case 21969:
                case 21970:
                case 21971:
                case 21972:
                case 21973:
                case 21974:
                case 21975:
                case 21976:
                case 21977:
                case 21978:
                case 30323:
                case 30324:
                case 30325:
                    return 5;
                default:
                    return 0;
            }
        }

        public void integerElement(int i, long j) throws ParserException {
            MatroskaExtractor matroskaExtractor = MatroskaExtractor.this;
            matroskaExtractor.getClass();
            if (i != 20529) {
                if (i != 20530) {
                    boolean z = true;
                    switch (i) {
                        case Token.LABEL /*131*/:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.d = (int) j;
                            return;
                        case Token.JSR /*136*/:
                            matroskaExtractor.b(i);
                            b bVar = matroskaExtractor.u;
                            if (j != 1) {
                                z = false;
                            }
                            bVar.av = z;
                            return;
                        case Token.CONST /*155*/:
                            matroskaExtractor.ai = matroskaExtractor.g(j);
                            return;
                        case Token.LETEXPR /*159*/:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.ao = (int) j;
                            return;
                        case 176:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.m = (int) j;
                            return;
                        case 179:
                            matroskaExtractor.a(i);
                            matroskaExtractor.ac.add(matroskaExtractor.g(j));
                            return;
                        case 186:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.n = (int) j;
                            return;
                        case 215:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.c = (int) j;
                            return;
                        case 231:
                            matroskaExtractor.ab = matroskaExtractor.g(j);
                            return;
                        case 238:
                            matroskaExtractor.ap = (int) j;
                            return;
                        case 241:
                            if (!matroskaExtractor.ae) {
                                matroskaExtractor.a(i);
                                matroskaExtractor.ad.add(j);
                                matroskaExtractor.ae = true;
                                return;
                            }
                            return;
                        case 251:
                            matroskaExtractor.aq = true;
                            return;
                        case 16871:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.g = (int) j;
                            return;
                        case 16980:
                            if (j != 3) {
                                StringBuilder sb = new StringBuilder(50);
                                sb.append("ContentCompAlgo ");
                                sb.append(j);
                                sb.append(" not supported");
                                throw new ParserException(sb.toString());
                            }
                            return;
                        case 17029:
                            if (j < 1 || j > 2) {
                                StringBuilder sb2 = new StringBuilder(53);
                                sb2.append("DocTypeReadVersion ");
                                sb2.append(j);
                                sb2.append(" not supported");
                                throw new ParserException(sb2.toString());
                            }
                            return;
                        case 17143:
                            if (j != 1) {
                                StringBuilder sb3 = new StringBuilder(50);
                                sb3.append("EBMLReadVersion ");
                                sb3.append(j);
                                sb3.append(" not supported");
                                throw new ParserException(sb3.toString());
                            }
                            return;
                        case 18401:
                            if (j != 5) {
                                StringBuilder sb4 = new StringBuilder(49);
                                sb4.append("ContentEncAlgo ");
                                sb4.append(j);
                                sb4.append(" not supported");
                                throw new ParserException(sb4.toString());
                            }
                            return;
                        case 18408:
                            if (j != 1) {
                                StringBuilder sb5 = new StringBuilder(56);
                                sb5.append("AESSettingsCipherMode ");
                                sb5.append(j);
                                sb5.append(" not supported");
                                throw new ParserException(sb5.toString());
                            }
                            return;
                        case 21420:
                            matroskaExtractor.x = j + matroskaExtractor.q;
                            return;
                        case 21432:
                            int i2 = (int) j;
                            matroskaExtractor.b(i);
                            if (i2 == 0) {
                                matroskaExtractor.u.w = 0;
                                return;
                            } else if (i2 == 1) {
                                matroskaExtractor.u.w = 2;
                                return;
                            } else if (i2 == 3) {
                                matroskaExtractor.u.w = 1;
                                return;
                            } else if (i2 == 15) {
                                matroskaExtractor.u.w = 3;
                                return;
                            } else {
                                return;
                            }
                        case 21680:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.o = (int) j;
                            return;
                        case 21682:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.q = (int) j;
                            return;
                        case 21690:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.p = (int) j;
                            return;
                        case 21930:
                            matroskaExtractor.b(i);
                            b bVar2 = matroskaExtractor.u;
                            if (j != 1) {
                                z = false;
                            }
                            bVar2.au = z;
                            return;
                        case 21998:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.f = (int) j;
                            return;
                        case 22186:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.ar = j;
                            return;
                        case 22203:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.as = j;
                            return;
                        case 25188:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.ap = (int) j;
                            return;
                        case 30321:
                            matroskaExtractor.b(i);
                            int i3 = (int) j;
                            if (i3 == 0) {
                                matroskaExtractor.u.r = 0;
                                return;
                            } else if (i3 == 1) {
                                matroskaExtractor.u.r = 1;
                                return;
                            } else if (i3 == 2) {
                                matroskaExtractor.u.r = 2;
                                return;
                            } else if (i3 == 3) {
                                matroskaExtractor.u.r = 3;
                                return;
                            } else {
                                return;
                            }
                        case 2352003:
                            matroskaExtractor.b(i);
                            matroskaExtractor.u.e = (int) j;
                            return;
                        case 2807729:
                            matroskaExtractor.r = j;
                            return;
                        default:
                            switch (i) {
                                case 21945:
                                    matroskaExtractor.b(i);
                                    int i4 = (int) j;
                                    if (i4 == 1) {
                                        matroskaExtractor.u.aa = 2;
                                        return;
                                    } else if (i4 == 2) {
                                        matroskaExtractor.u.aa = 1;
                                        return;
                                    } else {
                                        return;
                                    }
                                case 21946:
                                    matroskaExtractor.b(i);
                                    int i5 = (int) j;
                                    if (i5 != 1) {
                                        if (i5 == 16) {
                                            matroskaExtractor.u.z = 6;
                                            return;
                                        } else if (i5 == 18) {
                                            matroskaExtractor.u.z = 7;
                                            return;
                                        } else if (!(i5 == 6 || i5 == 7)) {
                                            return;
                                        }
                                    }
                                    matroskaExtractor.u.z = 3;
                                    return;
                                case 21947:
                                    matroskaExtractor.b(i);
                                    b bVar3 = matroskaExtractor.u;
                                    bVar3.x = true;
                                    int i6 = (int) j;
                                    if (i6 == 1) {
                                        bVar3.y = 1;
                                        return;
                                    } else if (i6 == 9) {
                                        bVar3.y = 6;
                                        return;
                                    } else if (i6 == 4 || i6 == 5 || i6 == 6 || i6 == 7) {
                                        bVar3.y = 2;
                                        return;
                                    } else {
                                        return;
                                    }
                                case 21948:
                                    matroskaExtractor.b(i);
                                    matroskaExtractor.u.ab = (int) j;
                                    return;
                                case 21949:
                                    matroskaExtractor.b(i);
                                    matroskaExtractor.u.ac = (int) j;
                                    return;
                                default:
                                    return;
                            }
                    }
                } else if (j != 1) {
                    StringBuilder sb6 = new StringBuilder(55);
                    sb6.append("ContentEncodingScope ");
                    sb6.append(j);
                    sb6.append(" not supported");
                    throw new ParserException(sb6.toString());
                }
            } else if (j != 0) {
                StringBuilder sb7 = new StringBuilder(55);
                sb7.append("ContentEncodingOrder ");
                sb7.append(j);
                sb7.append(" not supported");
                throw new ParserException(sb7.toString());
            }
        }

        public boolean isLevel1Element(int i) {
            MatroskaExtractor.this.getClass();
            return i == 357149030 || i == 524531317 || i == 475249515 || i == 374648427;
        }

        public void startMasterElement(int i, long j, long j2) throws ParserException {
            MatroskaExtractor matroskaExtractor = MatroskaExtractor.this;
            Assertions.checkStateNotNull(matroskaExtractor.ba);
            if (i == 160) {
                matroskaExtractor.aq = false;
            } else if (i == 174) {
                matroskaExtractor.u = new b();
            } else if (i == 187) {
                matroskaExtractor.ae = false;
            } else if (i == 19899) {
                matroskaExtractor.w = -1;
                matroskaExtractor.x = -1;
            } else if (i == 20533) {
                matroskaExtractor.b(i);
                matroskaExtractor.u.h = true;
            } else if (i == 21968) {
                matroskaExtractor.b(i);
                matroskaExtractor.u.x = true;
            } else if (i == 408125543) {
                long j3 = matroskaExtractor.q;
                if (j3 == -1 || j3 == j) {
                    matroskaExtractor.q = j;
                    matroskaExtractor.p = j2;
                    return;
                }
                throw new ParserException("Multiple Segment elements not supported");
            } else if (i == 475249515) {
                matroskaExtractor.ac = new LongArray();
                matroskaExtractor.ad = new LongArray();
            } else if (i != 524531317 || matroskaExtractor.v) {
            } else {
                if (!matroskaExtractor.d || matroskaExtractor.z == -1) {
                    matroskaExtractor.ba.seekMap(new SeekMap.Unseekable(matroskaExtractor.t));
                    matroskaExtractor.v = true;
                    return;
                }
                matroskaExtractor.y = true;
            }
        }

        public void stringElement(int i, String str) throws ParserException {
            MatroskaExtractor matroskaExtractor = MatroskaExtractor.this;
            matroskaExtractor.getClass();
            if (i == 134) {
                matroskaExtractor.b(i);
                matroskaExtractor.u.b = str;
            } else if (i != 17026) {
                if (i == 21358) {
                    matroskaExtractor.b(i);
                    matroskaExtractor.u.a = str;
                } else if (i == 2274716) {
                    matroskaExtractor.b(i);
                    matroskaExtractor.u.aw = str;
                }
            } else if (!"webm".equals(str) && !"matroska".equals(str)) {
                StringBuilder sb = new StringBuilder(y2.c(str, 22));
                sb.append("DocType ");
                sb.append(str);
                sb.append(" not supported");
                throw new ParserException(sb.toString());
            }
        }
    }

    public static final class b {
        public String a;
        public int aa = -1;
        public int ab = 1000;
        public int ac = 200;
        public float ad = -1.0f;
        public float ae = -1.0f;
        public float af = -1.0f;
        public float ag = -1.0f;
        public float ah = -1.0f;
        public float ai = -1.0f;
        public float aj = -1.0f;
        public float ak = -1.0f;
        public float al = -1.0f;
        public float am = -1.0f;
        public byte[] an;
        public int ao = 1;
        public int ap = -1;
        public int aq = 8000;
        public long ar = 0;
        public long as = 0;
        public c at;
        public boolean au;
        public boolean av = true;
        public String aw = "eng";
        public TrackOutput ax;
        public int ay;
        public String b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public boolean h;
        public byte[] i;
        public TrackOutput.CryptoData j;
        public byte[] k;
        public DrmInitData l;
        public int m = -1;
        public int n = -1;
        public int o = -1;
        public int p = -1;
        public int q = 0;
        public int r = -1;
        public float s = 0.0f;
        public float t = 0.0f;
        public float u = 0.0f;
        public byte[] v = null;
        public int w = -1;
        public boolean x = false;
        public int y = -1;
        public int z = -1;

        @EnsuresNonNull({"codecPrivate"})
        public final byte[] a(String str) throws ParserException {
            String str2;
            byte[] bArr = this.k;
            if (bArr != null) {
                return bArr;
            }
            String valueOf = String.valueOf(str);
            if (valueOf.length() != 0) {
                str2 = "Missing CodecPrivate for codec ".concat(valueOf);
            } else {
                str2 = new String("Missing CodecPrivate for codec ");
            }
            throw new ParserException(str2);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: com.google.android.exoplayer2.video.ColorInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: byte[]} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v36, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v64, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v65, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v66, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v151, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v153, resolved type: java.util.ArrayList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v155, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v157, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v158, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v160, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v161, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v162, resolved type: com.google.common.collect.ImmutableList} */
        /* JADX WARNING: type inference failed for: r7v0 */
        /* JADX WARNING: type inference failed for: r7v4 */
        /* JADX WARNING: type inference failed for: r7v7 */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x0288, code lost:
            r1 = null;
            r2 = null;
            r15 = "audio/raw";
            r3 = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x02eb, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x02ec, code lost:
            r1 = null;
            r2 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:0x02ee, code lost:
            r3 = -1;
            r1 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:129:0x02ef, code lost:
            r10 = -1;
            r1 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x0343, code lost:
            r15 = r3;
            r3 = -1;
            r10 = -1;
            r18 = r2;
            r2 = r1;
            r1 = r18;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:166:0x03ea, code lost:
            r15 = r2;
            r2 = null;
            r1 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:172:0x03fb, code lost:
            r15 = r2;
            r1 = null;
            r2 = null;
            r3 = 4096;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:193:0x0457, code lost:
            r15 = r2;
            r2 = null;
            r1 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:218:0x04b8, code lost:
            if (r1.readLong() == r2.getLeastSignificantBits()) goto L_0x04ba;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:238:0x0515, code lost:
            r4 = r0.an;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:239:0x0517, code lost:
            if (r4 == null) goto L_0x0528;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:240:0x0519, code lost:
            r4 = com.google.android.exoplayer2.video.DolbyVisionConfig.parse(new com.google.android.exoplayer2.util.ParsableByteArray(r4));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:241:0x0522, code lost:
            if (r4 == null) goto L_0x0528;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:242:0x0524, code lost:
            r2 = r4.a;
            r15 = "video/dolby-vision";
         */
        /* JADX WARNING: Code restructure failed: missing block: B:243:0x0528, code lost:
            r4 = r0.av | 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:244:0x052d, code lost:
            if (r0.au == false) goto L_0x0531;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:245:0x052f, code lost:
            r5 = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:246:0x0531, code lost:
            r5 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:247:0x0532, code lost:
            r4 = r4 | r5;
            r5 = new com.google.android.exoplayer2.Format.Builder();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:248:0x053c, code lost:
            if (com.google.android.exoplayer2.util.MimeTypes.isAudio(r15) == false) goto L_0x0550;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:249:0x053e, code lost:
            r5.setChannelCount(r0.ao).setSampleRate(r0.aq).setPcmEncoding(r10);
            r11 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:251:0x0554, code lost:
            if (com.google.android.exoplayer2.util.MimeTypes.isVideo(r15) == false) goto L_0x06e2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:253:0x0558, code lost:
            if (r0.q != 0) goto L_0x056c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:254:0x055a, code lost:
            r6 = r0.o;
            r8 = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:255:0x055d, code lost:
            if (r6 != -1) goto L_0x0561;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:256:0x055f, code lost:
            r6 = r0.m;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:0x0561, code lost:
            r0.o = r6;
            r6 = r0.p;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:258:0x0565, code lost:
            if (r6 != -1) goto L_0x0569;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:259:0x0567, code lost:
            r6 = r0.n;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:260:0x0569, code lost:
            r0.p = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:261:0x056c, code lost:
            r8 = -1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:262:0x056d, code lost:
            r6 = r0.o;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:263:0x056f, code lost:
            if (r6 == r8) goto L_0x0581;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:264:0x0571, code lost:
            r9 = r0.p;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:265:0x0573, code lost:
            if (r9 == r8) goto L_0x0581;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:266:0x0575, code lost:
            r6 = ((float) (r0.n * r6)) / ((float) (r0.m * r9));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:267:0x0581, code lost:
            r6 = -1.0f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:269:0x0585, code lost:
            if (r0.x == false) goto L_0x0655;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:271:0x058d, code lost:
            if (r0.ad == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:273:0x0593, code lost:
            if (r0.ae == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:275:0x0599, code lost:
            if (r0.af == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:277:0x059f, code lost:
            if (r0.ag == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:279:0x05a5, code lost:
            if (r0.ah == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:281:0x05ab, code lost:
            if (r0.ai == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:283:0x05b1, code lost:
            if (r0.aj == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:285:0x05b7, code lost:
            if (r0.ak == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:287:0x05bd, code lost:
            if (r0.al == -1.0f) goto L_0x0649;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:289:0x05c3, code lost:
            if (r0.am != -1.0f) goto L_0x05c7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:290:0x05c7, code lost:
            r7 = new byte[25];
            r9 = java.nio.ByteBuffer.wrap(r7).order(java.nio.ByteOrder.LITTLE_ENDIAN);
            r9.put((byte) 0);
            r9.putShort((short) ((int) ((r0.ad * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.ae * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.af * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.ag * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.ah * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.ai * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.aj * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) ((r0.ak * 50000.0f) + 0.5f)));
            r9.putShort((short) ((int) (r0.al + 0.5f)));
            r9.putShort((short) ((int) (r0.am + 0.5f)));
            r9.putShort((short) r0.ab);
            r9.putShort((short) r0.ac);
            r7 = r7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:291:0x0649, code lost:
            r7 = new com.google.android.exoplayer2.video.ColorInfo(r0.y, r0.aa, r0.z, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:292:0x0655, code lost:
            r9 = r0.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:293:0x0657, code lost:
            if (r9 == null) goto L_0x066d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:294:0x0659, code lost:
            r10 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.bf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:295:0x065f, code lost:
            if (r10.containsKey(r9) == false) goto L_0x066d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:296:0x0661, code lost:
            r8 = r10.get(r0.a).intValue();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:298:0x066f, code lost:
            if (r0.r != 0) goto L_0x06bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:300:0x0678, code lost:
            if (java.lang.Float.compare(r0.s, 0.0f) != 0) goto L_0x06bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:302:0x0680, code lost:
            if (java.lang.Float.compare(r0.t, 0.0f) != 0) goto L_0x06bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:304:0x0688, code lost:
            if (java.lang.Float.compare(r0.u, 0.0f) != 0) goto L_0x068b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:306:0x0693, code lost:
            if (java.lang.Float.compare(r0.t, 90.0f) != 0) goto L_0x0698;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:307:0x0695, code lost:
            r14 = 90;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:309:0x06a0, code lost:
            if (java.lang.Float.compare(r0.t, -180.0f) == 0) goto L_0x06ba;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:311:0x06aa, code lost:
            if (java.lang.Float.compare(r0.t, 180.0f) != 0) goto L_0x06ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:313:0x06b5, code lost:
            if (java.lang.Float.compare(r0.t, -90.0f) != 0) goto L_0x06bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:314:0x06b7, code lost:
            r14 = 270;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:315:0x06ba, code lost:
            r14 = org.mozilla.javascript.Context.VERSION_1_8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:316:0x06bd, code lost:
            r14 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:317:0x06be, code lost:
            r5.setWidth(r0.m).setHeight(r0.n).setPixelWidthHeightRatio(r6).setRotationDegrees(r14).setProjectionData(r0.v).setStereoMode(r0.w).setColorInfo(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:319:0x06e8, code lost:
            if ("application/x-subrip".equals(r15) != false) goto L_0x0713;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:321:0x06f0, code lost:
            if ("text/x-ssa".equals(r15) != false) goto L_0x0713;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:323:0x06f8, code lost:
            if ("application/vobsub".equals(r15) != false) goto L_0x0713;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:325:0x0700, code lost:
            if ("application/pgs".equals(r15) != false) goto L_0x0713;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:327:0x0708, code lost:
            if ("application/dvbsubs".equals(r15) == false) goto L_0x070b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:329:0x0712, code lost:
            throw new com.google.android.exoplayer2.ParserException("Unexpected MIME type.");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:330:0x0713, code lost:
            r11 = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:331:0x0714, code lost:
            r6 = r0.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:332:0x0716, code lost:
            if (r6 == null) goto L_0x0725;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:334:0x071e, code lost:
            if (com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.bf.containsKey(r6) != false) goto L_0x0725;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:335:0x0720, code lost:
            r5.setLabel(r0.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:336:0x0725, code lost:
            r1 = r5.setId(r21).setSampleMimeType(r15).setMaxInputSize(r3).setLanguage(r0.aw).setSelectionFlags(r4).setInitializationData(r1).setCodecs(r2).setDrmInitData(r0.l).build();
            r2 = r20.track(r0.c, r11);
            r0.ax = r2;
            r2.format(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:337:0x075c, code lost:
            return;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:222:0x04bf  */
        /* JADX WARNING: Removed duplicated region for block: B:225:0x04e8  */
        @org.checkerframework.checker.nullness.qual.RequiresNonNull({"codecId"})
        @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"this.output"})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput r20, int r21) throws com.google.android.exoplayer2.ParserException {
            /*
                r19 = this;
                r0 = r19
                java.lang.String r1 = r0.b
                r1.getClass()
                int r2 = r1.hashCode()
                r5 = 20
                r6 = 15
                r8 = 16
                r9 = 8
                r10 = 4
                r11 = 2
                r12 = 3
                r13 = 1
                r14 = 0
                switch(r2) {
                    case -2095576542: goto L_0x01b9;
                    case -2095575984: goto L_0x01ad;
                    case -1985379776: goto L_0x01a1;
                    case -1784763192: goto L_0x0195;
                    case -1730367663: goto L_0x0189;
                    case -1482641358: goto L_0x017d;
                    case -1482641357: goto L_0x0171;
                    case -1373388978: goto L_0x0165;
                    case -933872740: goto L_0x0157;
                    case -538363189: goto L_0x0149;
                    case -538363109: goto L_0x013b;
                    case -425012669: goto L_0x012d;
                    case -356037306: goto L_0x011f;
                    case 62923557: goto L_0x0111;
                    case 62923603: goto L_0x0103;
                    case 62927045: goto L_0x00f5;
                    case 82318131: goto L_0x00e7;
                    case 82338133: goto L_0x00d9;
                    case 82338134: goto L_0x00cb;
                    case 99146302: goto L_0x00bd;
                    case 444813526: goto L_0x00af;
                    case 542569478: goto L_0x00a1;
                    case 635596514: goto L_0x0093;
                    case 725948237: goto L_0x0086;
                    case 725957860: goto L_0x0079;
                    case 738597099: goto L_0x006c;
                    case 855502857: goto L_0x005f;
                    case 1422270023: goto L_0x0052;
                    case 1809237540: goto L_0x0045;
                    case 1950749482: goto L_0x0038;
                    case 1950789798: goto L_0x002b;
                    case 1951062397: goto L_0x001e;
                    default: goto L_0x001b;
                }
            L_0x001b:
                r1 = -1
                goto L_0x01c4
            L_0x001e:
                java.lang.String r2 = "A_OPUS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0027
                goto L_0x001b
            L_0x0027:
                r1 = 31
                goto L_0x01c4
            L_0x002b:
                java.lang.String r2 = "A_FLAC"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0034
                goto L_0x001b
            L_0x0034:
                r1 = 30
                goto L_0x01c4
            L_0x0038:
                java.lang.String r2 = "A_EAC3"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0041
                goto L_0x001b
            L_0x0041:
                r1 = 29
                goto L_0x01c4
            L_0x0045:
                java.lang.String r2 = "V_MPEG2"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x004e
                goto L_0x001b
            L_0x004e:
                r1 = 28
                goto L_0x01c4
            L_0x0052:
                java.lang.String r2 = "S_TEXT/UTF8"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x005b
                goto L_0x001b
            L_0x005b:
                r1 = 27
                goto L_0x01c4
            L_0x005f:
                java.lang.String r2 = "V_MPEGH/ISO/HEVC"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0068
                goto L_0x001b
            L_0x0068:
                r1 = 26
                goto L_0x01c4
            L_0x006c:
                java.lang.String r2 = "S_TEXT/ASS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0075
                goto L_0x001b
            L_0x0075:
                r1 = 25
                goto L_0x01c4
            L_0x0079:
                java.lang.String r2 = "A_PCM/INT/LIT"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0082
                goto L_0x001b
            L_0x0082:
                r1 = 24
                goto L_0x01c4
            L_0x0086:
                java.lang.String r2 = "A_PCM/INT/BIG"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x008f
                goto L_0x001b
            L_0x008f:
                r1 = 23
                goto L_0x01c4
            L_0x0093:
                java.lang.String r2 = "A_PCM/FLOAT/IEEE"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x009d
                goto L_0x001b
            L_0x009d:
                r1 = 22
                goto L_0x01c4
            L_0x00a1:
                java.lang.String r2 = "A_DTS/EXPRESS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00ab
                goto L_0x001b
            L_0x00ab:
                r1 = 21
                goto L_0x01c4
            L_0x00af:
                java.lang.String r2 = "V_THEORA"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00b9
                goto L_0x001b
            L_0x00b9:
                r1 = 20
                goto L_0x01c4
            L_0x00bd:
                java.lang.String r2 = "S_HDMV/PGS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00c7
                goto L_0x001b
            L_0x00c7:
                r1 = 19
                goto L_0x01c4
            L_0x00cb:
                java.lang.String r2 = "V_VP9"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00d5
                goto L_0x001b
            L_0x00d5:
                r1 = 18
                goto L_0x01c4
            L_0x00d9:
                java.lang.String r2 = "V_VP8"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00e3
                goto L_0x001b
            L_0x00e3:
                r1 = 17
                goto L_0x01c4
            L_0x00e7:
                java.lang.String r2 = "V_AV1"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00f1
                goto L_0x001b
            L_0x00f1:
                r1 = 16
                goto L_0x01c4
            L_0x00f5:
                java.lang.String r2 = "A_DTS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x00ff
                goto L_0x001b
            L_0x00ff:
                r1 = 15
                goto L_0x01c4
            L_0x0103:
                java.lang.String r2 = "A_AC3"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x010d
                goto L_0x001b
            L_0x010d:
                r1 = 14
                goto L_0x01c4
            L_0x0111:
                java.lang.String r2 = "A_AAC"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x011b
                goto L_0x001b
            L_0x011b:
                r1 = 13
                goto L_0x01c4
            L_0x011f:
                java.lang.String r2 = "A_DTS/LOSSLESS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0129
                goto L_0x001b
            L_0x0129:
                r1 = 12
                goto L_0x01c4
            L_0x012d:
                java.lang.String r2 = "S_VOBSUB"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0137
                goto L_0x001b
            L_0x0137:
                r1 = 11
                goto L_0x01c4
            L_0x013b:
                java.lang.String r2 = "V_MPEG4/ISO/AVC"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0145
                goto L_0x001b
            L_0x0145:
                r1 = 10
                goto L_0x01c4
            L_0x0149:
                java.lang.String r2 = "V_MPEG4/ISO/ASP"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0153
                goto L_0x001b
            L_0x0153:
                r1 = 9
                goto L_0x01c4
            L_0x0157:
                java.lang.String r2 = "S_DVBSUB"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0161
                goto L_0x001b
            L_0x0161:
                r1 = 8
                goto L_0x01c4
            L_0x0165:
                java.lang.String r2 = "V_MS/VFW/FOURCC"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x016f
                goto L_0x001b
            L_0x016f:
                r1 = 7
                goto L_0x01c4
            L_0x0171:
                java.lang.String r2 = "A_MPEG/L3"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x017b
                goto L_0x001b
            L_0x017b:
                r1 = 6
                goto L_0x01c4
            L_0x017d:
                java.lang.String r2 = "A_MPEG/L2"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0187
                goto L_0x001b
            L_0x0187:
                r1 = 5
                goto L_0x01c4
            L_0x0189:
                java.lang.String r2 = "A_VORBIS"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x0193
                goto L_0x001b
            L_0x0193:
                r1 = 4
                goto L_0x01c4
            L_0x0195:
                java.lang.String r2 = "A_TRUEHD"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x019f
                goto L_0x001b
            L_0x019f:
                r1 = 3
                goto L_0x01c4
            L_0x01a1:
                java.lang.String r2 = "A_MS/ACM"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x01ab
                goto L_0x001b
            L_0x01ab:
                r1 = 2
                goto L_0x01c4
            L_0x01ad:
                java.lang.String r2 = "V_MPEG4/ISO/SP"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x01b7
                goto L_0x001b
            L_0x01b7:
                r1 = 1
                goto L_0x01c4
            L_0x01b9:
                java.lang.String r2 = "V_MPEG4/ISO/AP"
                boolean r1 = r1.equals(r2)
                if (r1 != 0) goto L_0x01c3
                goto L_0x001b
            L_0x01c3:
                r1 = 0
            L_0x01c4:
                java.lang.String r2 = "video/x-unknown"
                java.lang.String r3 = ". Setting mimeType to audio/x-unknown"
                java.lang.String r17 = "audio/raw"
                java.lang.String r15 = "audio/x-unknown"
                java.lang.String r4 = "MatroskaExtractor"
                r7 = 0
                switch(r1) {
                    case 0: goto L_0x0507;
                    case 1: goto L_0x0507;
                    case 2: goto L_0x0484;
                    case 3: goto L_0x0479;
                    case 4: goto L_0x0402;
                    case 5: goto L_0x03f9;
                    case 6: goto L_0x03f6;
                    case 7: goto L_0x0360;
                    case 8: goto L_0x034d;
                    case 9: goto L_0x0507;
                    case 10: goto L_0x032a;
                    case 11: goto L_0x031c;
                    case 12: goto L_0x0319;
                    case 13: goto L_0x02fb;
                    case 14: goto L_0x02f8;
                    case 15: goto L_0x02f5;
                    case 16: goto L_0x02f2;
                    case 17: goto L_0x02e9;
                    case 18: goto L_0x02e6;
                    case 19: goto L_0x02e3;
                    case 20: goto L_0x02eb;
                    case 21: goto L_0x02f5;
                    case 22: goto L_0x02be;
                    case 23: goto L_0x028f;
                    case 24: goto L_0x025f;
                    case 25: goto L_0x024f;
                    case 26: goto L_0x0234;
                    case 27: goto L_0x0230;
                    case 28: goto L_0x022c;
                    case 29: goto L_0x0228;
                    case 30: goto L_0x021a;
                    case 31: goto L_0x01da;
                    default: goto L_0x01d2;
                }
            L_0x01d2:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Unrecognized codec identifier."
                r1.<init>((java.lang.String) r2)
                throw r1
            L_0x01da:
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>(r12)
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                r1.add(r2)
                java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r9)
                java.nio.ByteOrder r3 = java.nio.ByteOrder.LITTLE_ENDIAN
                java.nio.ByteBuffer r2 = r2.order(r3)
                long r4 = r0.ar
                java.nio.ByteBuffer r2 = r2.putLong(r4)
                byte[] r2 = r2.array()
                r1.add(r2)
                java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r9)
                java.nio.ByteBuffer r2 = r2.order(r3)
                long r3 = r0.as
                java.nio.ByteBuffer r2 = r2.putLong(r3)
                byte[] r2 = r2.array()
                r1.add(r2)
                java.lang.String r2 = "audio/opus"
                r3 = 5760(0x1680, float:8.071E-42)
                goto L_0x0457
            L_0x021a:
                java.lang.String r1 = r0.b
                byte[] r1 = r0.a(r1)
                java.util.List r1 = java.util.Collections.singletonList(r1)
                java.lang.String r2 = "audio/flac"
                goto L_0x03ea
            L_0x0228:
                java.lang.String r2 = "audio/eac3"
                goto L_0x02eb
            L_0x022c:
                java.lang.String r2 = "video/mpeg2"
                goto L_0x02eb
            L_0x0230:
                java.lang.String r2 = "application/x-subrip"
                goto L_0x02eb
            L_0x0234:
                com.google.android.exoplayer2.util.ParsableByteArray r1 = new com.google.android.exoplayer2.util.ParsableByteArray
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                r1.<init>((byte[]) r2)
                com.google.android.exoplayer2.video.HevcConfig r1 = com.google.android.exoplayer2.video.HevcConfig.parse(r1)
                java.util.List<byte[]> r2 = r1.a
                int r3 = r1.b
                r0.ay = r3
                java.lang.String r3 = "video/hevc"
                java.lang.String r1 = r1.c
                goto L_0x0343
            L_0x024f:
                byte[] r1 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.bc
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                com.google.common.collect.ImmutableList r1 = com.google.common.collect.ImmutableList.of(r1, r2)
                java.lang.String r2 = "text/x-ssa"
                goto L_0x03ea
            L_0x025f:
                int r1 = r0.ap
                int r10 = com.google.android.exoplayer2.util.Util.getPcmEncoding(r1)
                if (r10 != 0) goto L_0x0288
                int r1 = r0.ap
                int r2 = r15.length()
                int r2 = r2 + 74
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>(r2)
                java.lang.String r2 = "Unsupported little endian PCM bit depth: "
                r5.append(r2)
                r5.append(r1)
                r5.append(r3)
                java.lang.String r1 = r5.toString()
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                goto L_0x02ec
            L_0x0288:
                r1 = r7
                r2 = r1
                r15 = r17
                r3 = -1
                goto L_0x0515
            L_0x028f:
                int r1 = r0.ap
                if (r1 != r9) goto L_0x029b
                r1 = r7
                r2 = r1
                r15 = r17
                r3 = -1
                r10 = 3
                goto L_0x0515
            L_0x029b:
                if (r1 != r8) goto L_0x02a0
                r10 = 268435456(0x10000000, float:2.5243549E-29)
                goto L_0x0288
            L_0x02a0:
                int r2 = r15.length()
                int r2 = r2 + 71
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>(r2)
                java.lang.String r2 = "Unsupported big endian PCM bit depth: "
                r5.append(r2)
                r5.append(r1)
                r5.append(r3)
                java.lang.String r1 = r5.toString()
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                goto L_0x02ec
            L_0x02be:
                int r1 = r0.ap
                r2 = 32
                if (r1 != r2) goto L_0x02c5
                goto L_0x0288
            L_0x02c5:
                int r2 = r15.length()
                int r2 = r2 + 75
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>(r2)
                java.lang.String r2 = "Unsupported floating point PCM bit depth: "
                r5.append(r2)
                r5.append(r1)
                r5.append(r3)
                java.lang.String r1 = r5.toString()
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                goto L_0x02ec
            L_0x02e3:
                java.lang.String r2 = "application/pgs"
                goto L_0x02eb
            L_0x02e6:
                java.lang.String r2 = "video/x-vnd.on2.vp9"
                goto L_0x02eb
            L_0x02e9:
                java.lang.String r2 = "video/x-vnd.on2.vp8"
            L_0x02eb:
                r15 = r2
            L_0x02ec:
                r1 = r7
                r2 = r1
            L_0x02ee:
                r3 = -1
            L_0x02ef:
                r10 = -1
                goto L_0x0515
            L_0x02f2:
                java.lang.String r2 = "video/av01"
                goto L_0x02eb
            L_0x02f5:
                java.lang.String r2 = "audio/vnd.dts"
                goto L_0x02eb
            L_0x02f8:
                java.lang.String r2 = "audio/ac3"
                goto L_0x02eb
            L_0x02fb:
                java.lang.String r1 = r0.b
                byte[] r1 = r0.a(r1)
                java.util.List r1 = java.util.Collections.singletonList(r1)
                byte[] r2 = r0.k
                com.google.android.exoplayer2.audio.AacUtil$Config r2 = com.google.android.exoplayer2.audio.AacUtil.parseAudioSpecificConfig(r2)
                int r3 = r2.a
                r0.aq = r3
                int r3 = r2.b
                r0.ao = r3
                java.lang.String r3 = "audio/mp4a-latm"
                java.lang.String r2 = r2.c
                r15 = r3
                goto L_0x02ee
            L_0x0319:
                java.lang.String r2 = "audio/vnd.dts.hd"
                goto L_0x02eb
            L_0x031c:
                java.lang.String r1 = r0.b
                byte[] r1 = r0.a(r1)
                com.google.common.collect.ImmutableList r1 = com.google.common.collect.ImmutableList.of(r1)
                java.lang.String r2 = "application/vobsub"
                goto L_0x03ea
            L_0x032a:
                com.google.android.exoplayer2.util.ParsableByteArray r1 = new com.google.android.exoplayer2.util.ParsableByteArray
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                r1.<init>((byte[]) r2)
                com.google.android.exoplayer2.video.AvcConfig r1 = com.google.android.exoplayer2.video.AvcConfig.parse(r1)
                java.util.List<byte[]> r2 = r1.a
                int r3 = r1.b
                r0.ay = r3
                java.lang.String r3 = "video/avc"
                java.lang.String r1 = r1.f
            L_0x0343:
                r15 = r3
                r3 = -1
                r10 = -1
                r18 = r2
                r2 = r1
                r1 = r18
                goto L_0x0515
            L_0x034d:
                byte[] r1 = new byte[r10]
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                java.lang.System.arraycopy(r2, r14, r1, r14, r10)
                com.google.common.collect.ImmutableList r1 = com.google.common.collect.ImmutableList.of(r1)
                java.lang.String r2 = "application/dvbsubs"
                goto L_0x03ea
            L_0x0360:
                com.google.android.exoplayer2.util.ParsableByteArray r1 = new com.google.android.exoplayer2.util.ParsableByteArray
                java.lang.String r3 = r0.b
                byte[] r3 = r0.a(r3)
                r1.<init>((byte[]) r3)
                r1.skipBytes(r8)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                long r8 = r1.readLittleEndianUnsignedInt()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                r15 = 1482049860(0x58564944, double:7.322299212E-315)
                int r3 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
                if (r3 != 0) goto L_0x0381
                android.util.Pair r1 = new android.util.Pair     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                java.lang.String r2 = "video/divx"
                r1.<init>(r2, r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                goto L_0x03e2
            L_0x0381:
                r15 = 859189832(0x33363248, double:4.244961792E-315)
                int r3 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
                if (r3 != 0) goto L_0x0390
                android.util.Pair r1 = new android.util.Pair     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                java.lang.String r2 = "video/3gpp"
                r1.<init>(r2, r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                goto L_0x03e2
            L_0x0390:
                r15 = 826496599(0x31435657, double:4.08343576E-315)
                int r3 = (r8 > r15 ? 1 : (r8 == r15 ? 0 : -1))
                if (r3 != 0) goto L_0x03d8
                int r2 = r1.getPosition()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                int r2 = r2 + r5
                byte[] r1 = r1.getData()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
            L_0x03a0:
                int r3 = r1.length     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                int r3 = r3 + -4
                if (r2 >= r3) goto L_0x03d0
                byte r3 = r1[r2]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                if (r3 != 0) goto L_0x03cd
                int r3 = r2 + 1
                byte r3 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                if (r3 != 0) goto L_0x03cd
                int r3 = r2 + 2
                byte r3 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                if (r3 != r13) goto L_0x03cd
                int r3 = r2 + 3
                byte r3 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                if (r3 != r6) goto L_0x03cd
                int r3 = r1.length     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                byte[] r1 = java.util.Arrays.copyOfRange(r1, r2, r3)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                android.util.Pair r2 = new android.util.Pair     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                java.lang.String r3 = "video/wvc1"
                java.util.List r1 = java.util.Collections.singletonList(r1)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                r2.<init>(r3, r1)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                r1 = r2
                goto L_0x03e2
            L_0x03cd:
                int r2 = r2 + 1
                goto L_0x03a0
            L_0x03d0:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                java.lang.String r2 = "Failed to find FourCC VC1 initialization data"
                r1.<init>((java.lang.String) r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
                throw r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x03ee }
            L_0x03d8:
                java.lang.String r1 = "Unknown FourCC. Setting mimeType to video/x-unknown"
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                android.util.Pair r1 = new android.util.Pair
                r1.<init>(r2, r7)
            L_0x03e2:
                java.lang.Object r2 = r1.first
                java.lang.String r2 = (java.lang.String) r2
                java.lang.Object r1 = r1.second
                java.util.List r1 = (java.util.List) r1
            L_0x03ea:
                r15 = r2
                r2 = r7
                goto L_0x02ee
            L_0x03ee:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Error parsing FourCC private data"
                r1.<init>((java.lang.String) r2)
                throw r1
            L_0x03f6:
                java.lang.String r2 = "audio/mpeg"
                goto L_0x03fb
            L_0x03f9:
                java.lang.String r2 = "audio/mpeg-L2"
            L_0x03fb:
                r15 = r2
                r1 = r7
                r2 = r1
                r3 = 4096(0x1000, float:5.74E-42)
                goto L_0x02ef
            L_0x0402:
                java.lang.String r1 = r0.b
                byte[] r1 = r0.a(r1)
                java.lang.String r2 = "Error parsing vorbis codec private"
                byte r3 = r1[r14]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                if (r3 != r11) goto L_0x046d
                r3 = 1
                r4 = 0
            L_0x0410:
                byte r5 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r6 = 255(0xff, float:3.57E-43)
                r5 = r5 & r6
                if (r5 != r6) goto L_0x041c
                int r4 = r4 + 255
                int r3 = r3 + 1
                goto L_0x0410
            L_0x041c:
                int r3 = r3 + r13
                int r4 = r4 + r5
                r5 = 0
            L_0x041f:
                byte r8 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r8 = r8 & r6
                if (r8 != r6) goto L_0x0429
                int r5 = r5 + 255
                int r3 = r3 + 1
                goto L_0x041f
            L_0x0429:
                int r3 = r3 + r13
                int r5 = r5 + r8
                byte r6 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                if (r6 != r13) goto L_0x0467
                byte[] r6 = new byte[r4]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                java.lang.System.arraycopy(r1, r3, r6, r14, r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                int r3 = r3 + r4
                byte r4 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                if (r4 != r12) goto L_0x0461
                int r3 = r3 + r5
                byte r4 = r1[r3]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r5 = 5
                if (r4 != r5) goto L_0x045b
                int r4 = r1.length     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                int r4 = r4 - r3
                byte[] r4 = new byte[r4]     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                int r5 = r1.length     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                int r5 = r5 - r3
                java.lang.System.arraycopy(r1, r3, r4, r14, r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.<init>(r11)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.add(r6)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.add(r4)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                java.lang.String r2 = "audio/vorbis"
                r3 = 8192(0x2000, float:1.14794E-41)
            L_0x0457:
                r15 = r2
                r2 = r7
                goto L_0x02ef
            L_0x045b:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.<init>((java.lang.String) r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                throw r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
            L_0x0461:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.<init>((java.lang.String) r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                throw r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
            L_0x0467:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.<init>((java.lang.String) r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                throw r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
            L_0x046d:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                r1.<init>((java.lang.String) r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
                throw r1     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0473 }
            L_0x0473:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
                r1.<init>((java.lang.String) r2)
                throw r1
            L_0x0479:
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$c r1 = new com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$c
                r1.<init>()
                r0.at = r1
                java.lang.String r2 = "audio/true-hd"
                goto L_0x02eb
            L_0x0484:
                com.google.android.exoplayer2.util.ParsableByteArray r1 = new com.google.android.exoplayer2.util.ParsableByteArray
                java.lang.String r2 = r0.b
                byte[] r2 = r0.a(r2)
                r1.<init>((byte[]) r2)
                int r2 = r1.readLittleEndianUnsignedShort()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                if (r2 != r13) goto L_0x0496
                goto L_0x04ba
            L_0x0496:
                r5 = 65534(0xfffe, float:9.1833E-41)
                if (r2 != r5) goto L_0x04bc
                r2 = 24
                r1.setPosition(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                long r5 = r1.readLong()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                java.util.UUID r2 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.be     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                long r8 = r2.getMostSignificantBits()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                int r10 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
                if (r10 != 0) goto L_0x04bc
                long r5 = r1.readLong()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                long r1 = r2.getLeastSignificantBits()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x04ff }
                int r8 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
                if (r8 != 0) goto L_0x04bc
            L_0x04ba:
                r1 = 1
                goto L_0x04bd
            L_0x04bc:
                r1 = 0
            L_0x04bd:
                if (r1 == 0) goto L_0x04e8
                int r1 = r0.ap
                int r10 = com.google.android.exoplayer2.util.Util.getPcmEncoding(r1)
                if (r10 != 0) goto L_0x0288
                int r1 = r0.ap
                int r2 = r15.length()
                int r2 = r2 + 60
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>(r2)
                java.lang.String r2 = "Unsupported PCM bit depth: "
                r5.append(r2)
                r5.append(r1)
                r5.append(r3)
                java.lang.String r1 = r5.toString()
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                goto L_0x02ec
            L_0x04e8:
                int r1 = r15.length()
                java.lang.String r2 = "Non-PCM MS/ACM is unsupported. Setting mimeType to "
                if (r1 == 0) goto L_0x04f5
                java.lang.String r1 = r2.concat(r15)
                goto L_0x04fa
            L_0x04f5:
                java.lang.String r1 = new java.lang.String
                r1.<init>(r2)
            L_0x04fa:
                com.google.android.exoplayer2.util.Log.w(r4, r1)
                goto L_0x02ec
            L_0x04ff:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Error parsing MS/ACM codec private"
                r1.<init>((java.lang.String) r2)
                throw r1
            L_0x0507:
                byte[] r1 = r0.k
                if (r1 != 0) goto L_0x050d
                r1 = r7
                goto L_0x0511
            L_0x050d:
                java.util.List r1 = java.util.Collections.singletonList(r1)
            L_0x0511:
                java.lang.String r2 = "video/mp4v-es"
                goto L_0x03ea
            L_0x0515:
                byte[] r4 = r0.an
                if (r4 == 0) goto L_0x0528
                com.google.android.exoplayer2.util.ParsableByteArray r5 = new com.google.android.exoplayer2.util.ParsableByteArray
                r5.<init>((byte[]) r4)
                com.google.android.exoplayer2.video.DolbyVisionConfig r4 = com.google.android.exoplayer2.video.DolbyVisionConfig.parse(r5)
                if (r4 == 0) goto L_0x0528
                java.lang.String r2 = r4.a
                java.lang.String r15 = "video/dolby-vision"
            L_0x0528:
                boolean r4 = r0.av
                r4 = r4 | r14
                boolean r5 = r0.au
                if (r5 == 0) goto L_0x0531
                r5 = 2
                goto L_0x0532
            L_0x0531:
                r5 = 0
            L_0x0532:
                r4 = r4 | r5
                com.google.android.exoplayer2.Format$Builder r5 = new com.google.android.exoplayer2.Format$Builder
                r5.<init>()
                boolean r6 = com.google.android.exoplayer2.util.MimeTypes.isAudio(r15)
                if (r6 == 0) goto L_0x0550
                int r6 = r0.ao
                com.google.android.exoplayer2.Format$Builder r6 = r5.setChannelCount(r6)
                int r7 = r0.aq
                com.google.android.exoplayer2.Format$Builder r6 = r6.setSampleRate(r7)
                r6.setPcmEncoding(r10)
                r11 = 1
                goto L_0x0714
            L_0x0550:
                boolean r6 = com.google.android.exoplayer2.util.MimeTypes.isVideo(r15)
                if (r6 == 0) goto L_0x06e2
                int r6 = r0.q
                if (r6 != 0) goto L_0x056c
                int r6 = r0.o
                r8 = -1
                if (r6 != r8) goto L_0x0561
                int r6 = r0.m
            L_0x0561:
                r0.o = r6
                int r6 = r0.p
                if (r6 != r8) goto L_0x0569
                int r6 = r0.n
            L_0x0569:
                r0.p = r6
                goto L_0x056d
            L_0x056c:
                r8 = -1
            L_0x056d:
                int r6 = r0.o
                if (r6 == r8) goto L_0x0581
                int r9 = r0.p
                if (r9 == r8) goto L_0x0581
                int r10 = r0.n
                int r10 = r10 * r6
                float r6 = (float) r10
                int r10 = r0.m
                int r10 = r10 * r9
                float r9 = (float) r10
                float r6 = r6 / r9
                goto L_0x0583
            L_0x0581:
                r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            L_0x0583:
                boolean r9 = r0.x
                if (r9 == 0) goto L_0x0655
                float r9 = r0.ad
                r10 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.ae
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.af
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.ag
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.ah
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.ai
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.aj
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.ak
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.al
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 == 0) goto L_0x0649
                float r9 = r0.am
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 != 0) goto L_0x05c7
                goto L_0x0649
            L_0x05c7:
                r9 = 25
                byte[] r7 = new byte[r9]
                java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r7)
                java.nio.ByteOrder r10 = java.nio.ByteOrder.LITTLE_ENDIAN
                java.nio.ByteBuffer r9 = r9.order(r10)
                r9.put(r14)
                float r10 = r0.ad
                r12 = 1195593728(0x47435000, float:50000.0)
                float r10 = r10 * r12
                r13 = 1056964608(0x3f000000, float:0.5)
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.ae
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.af
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.ag
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.ah
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.ai
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.aj
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.ak
                float r10 = r10 * r12
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.al
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                float r10 = r0.am
                float r10 = r10 + r13
                int r10 = (int) r10
                short r10 = (short) r10
                r9.putShort(r10)
                int r10 = r0.ab
                short r10 = (short) r10
                r9.putShort(r10)
                int r10 = r0.ac
                short r10 = (short) r10
                r9.putShort(r10)
            L_0x0649:
                com.google.android.exoplayer2.video.ColorInfo r9 = new com.google.android.exoplayer2.video.ColorInfo
                int r10 = r0.y
                int r12 = r0.aa
                int r13 = r0.z
                r9.<init>(r10, r12, r13, r7)
                r7 = r9
            L_0x0655:
                java.lang.String r9 = r0.a
                if (r9 == 0) goto L_0x066d
                java.util.Map<java.lang.String, java.lang.Integer> r10 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.bf
                boolean r9 = r10.containsKey(r9)
                if (r9 == 0) goto L_0x066d
                java.lang.String r8 = r0.a
                java.lang.Object r8 = r10.get(r8)
                java.lang.Integer r8 = (java.lang.Integer) r8
                int r8 = r8.intValue()
            L_0x066d:
                int r9 = r0.r
                if (r9 != 0) goto L_0x06bd
                float r9 = r0.s
                r10 = 0
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x06bd
                float r9 = r0.t
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x06bd
                float r9 = r0.u
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x068b
                goto L_0x06be
            L_0x068b:
                float r9 = r0.t
                r10 = 1119092736(0x42b40000, float:90.0)
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x0698
                r14 = 90
                goto L_0x06be
            L_0x0698:
                float r9 = r0.t
                r10 = -1020002304(0xffffffffc3340000, float:-180.0)
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 == 0) goto L_0x06ba
                float r9 = r0.t
                r10 = 1127481344(0x43340000, float:180.0)
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x06ad
                goto L_0x06ba
            L_0x06ad:
                float r9 = r0.t
                r10 = -1028390912(0xffffffffc2b40000, float:-90.0)
                int r9 = java.lang.Float.compare(r9, r10)
                if (r9 != 0) goto L_0x06bd
                r14 = 270(0x10e, float:3.78E-43)
                goto L_0x06be
            L_0x06ba:
                r14 = 180(0xb4, float:2.52E-43)
                goto L_0x06be
            L_0x06bd:
                r14 = r8
            L_0x06be:
                int r8 = r0.m
                com.google.android.exoplayer2.Format$Builder r8 = r5.setWidth(r8)
                int r9 = r0.n
                com.google.android.exoplayer2.Format$Builder r8 = r8.setHeight(r9)
                com.google.android.exoplayer2.Format$Builder r6 = r8.setPixelWidthHeightRatio(r6)
                com.google.android.exoplayer2.Format$Builder r6 = r6.setRotationDegrees(r14)
                byte[] r8 = r0.v
                com.google.android.exoplayer2.Format$Builder r6 = r6.setProjectionData(r8)
                int r8 = r0.w
                com.google.android.exoplayer2.Format$Builder r6 = r6.setStereoMode(r8)
                r6.setColorInfo(r7)
                goto L_0x0714
            L_0x06e2:
                java.lang.String r6 = "application/x-subrip"
                boolean r6 = r6.equals(r15)
                if (r6 != 0) goto L_0x0713
                java.lang.String r6 = "text/x-ssa"
                boolean r6 = r6.equals(r15)
                if (r6 != 0) goto L_0x0713
                java.lang.String r6 = "application/vobsub"
                boolean r6 = r6.equals(r15)
                if (r6 != 0) goto L_0x0713
                java.lang.String r6 = "application/pgs"
                boolean r6 = r6.equals(r15)
                if (r6 != 0) goto L_0x0713
                java.lang.String r6 = "application/dvbsubs"
                boolean r6 = r6.equals(r15)
                if (r6 == 0) goto L_0x070b
                goto L_0x0713
            L_0x070b:
                com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
                java.lang.String r2 = "Unexpected MIME type."
                r1.<init>((java.lang.String) r2)
                throw r1
            L_0x0713:
                r11 = 3
            L_0x0714:
                java.lang.String r6 = r0.a
                if (r6 == 0) goto L_0x0725
                java.util.Map<java.lang.String, java.lang.Integer> r7 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.bf
                boolean r6 = r7.containsKey(r6)
                if (r6 != 0) goto L_0x0725
                java.lang.String r6 = r0.a
                r5.setLabel(r6)
            L_0x0725:
                r6 = r21
                com.google.android.exoplayer2.Format$Builder r5 = r5.setId((int) r6)
                com.google.android.exoplayer2.Format$Builder r5 = r5.setSampleMimeType(r15)
                com.google.android.exoplayer2.Format$Builder r3 = r5.setMaxInputSize(r3)
                java.lang.String r5 = r0.aw
                com.google.android.exoplayer2.Format$Builder r3 = r3.setLanguage(r5)
                com.google.android.exoplayer2.Format$Builder r3 = r3.setSelectionFlags(r4)
                com.google.android.exoplayer2.Format$Builder r1 = r3.setInitializationData(r1)
                com.google.android.exoplayer2.Format$Builder r1 = r1.setCodecs(r2)
                com.google.android.exoplayer2.drm.DrmInitData r2 = r0.l
                com.google.android.exoplayer2.Format$Builder r1 = r1.setDrmInitData(r2)
                com.google.android.exoplayer2.Format r1 = r1.build()
                int r2 = r0.c
                r3 = r20
                com.google.android.exoplayer2.extractor.TrackOutput r2 = r3.track(r2, r11)
                r0.ax = r2
                r2.format(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.b.initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput, int):void");
        }

        @RequiresNonNull({"output"})
        public void outputPendingSampleMetadata() {
            c cVar = this.at;
            if (cVar != null) {
                cVar.outputPendingSampleMetadata(this);
            }
        }

        public void reset() {
            c cVar = this.at;
            if (cVar != null) {
                cVar.reset();
            }
        }
    }

    public static final class c {
        public final byte[] a = new byte[10];
        public boolean b;
        public int c;
        public long d;
        public int e;
        public int f;
        public int g;

        @RequiresNonNull({"#1.output"})
        public void outputPendingSampleMetadata(b bVar) {
            if (this.c > 0) {
                bVar.ax.sampleMetadata(this.d, this.e, this.f, this.g, bVar.j);
                this.c = 0;
            }
        }

        public void reset() {
            this.b = false;
            this.c = 0;
        }

        @RequiresNonNull({"#1.output"})
        public void sampleMetadata(b bVar, long j, int i, int i2, int i3) {
            if (this.b) {
                int i4 = this.c;
                int i5 = i4 + 1;
                this.c = i5;
                if (i4 == 0) {
                    this.d = j;
                    this.e = i;
                    this.f = 0;
                }
                this.f += i2;
                this.g = i3;
                if (i5 >= 16) {
                    outputPendingSampleMetadata(bVar);
                }
            }
        }

        public void startSample(ExtractorInput extractorInput) throws IOException {
            if (!this.b) {
                byte[] bArr = this.a;
                extractorInput.peekFully(bArr, 0, 10);
                extractorInput.resetPeekPosition();
                if (Ac3Util.parseTrueHdSyncframeAudioSampleCount(bArr) != 0) {
                    this.b = true;
                }
            }
        }
    }

    static {
        HashMap hashMap = new HashMap();
        y2.u(0, hashMap, "htc_video_rotA-000", 90, "htc_video_rotA-090", Context.VERSION_1_8, "htc_video_rotA-180", 270, "htc_video_rotA-270");
        bf = Collections.unmodifiableMap(hashMap);
    }

    public MatroskaExtractor() {
        this(0);
    }

    public static byte[] d(long j2, long j3, String str) {
        boolean z2;
        if (j2 != -9223372036854775807L) {
            z2 = true;
        } else {
            z2 = false;
        }
        Assertions.checkArgument(z2);
        int i2 = (int) (j2 / 3600000000L);
        long j4 = j2 - (((long) (i2 * 3600)) * 1000000);
        int i3 = (int) (j4 / 60000000);
        long j5 = j4 - (((long) (i3 * 60)) * 1000000);
        int i4 = (int) (j5 / 1000000);
        int i5 = (int) ((j5 - (((long) i4) * 1000000)) / j3);
        return Util.getUtf8Bytes(String.format(Locale.US, str, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)}));
    }

    @EnsuresNonNull({"cueTimesUs", "cueClusterPositions"})
    public final void a(int i2) throws ParserException {
        if (this.ac == null || this.ad == null) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Element ");
            sb.append(i2);
            sb.append(" must be in a Cues");
            throw new ParserException(sb.toString());
        }
    }

    @EnsuresNonNull({"currentTrack"})
    public final void b(int i2) throws ParserException {
        if (this.u == null) {
            StringBuilder sb = new StringBuilder(43);
            sb.append("Element ");
            sb.append(i2);
            sb.append(" must be in a TrackEntry");
            throw new ParserException(sb.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b0  */
    @org.checkerframework.checker.nullness.qual.RequiresNonNull({"#1.output"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void c(com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.b r17, long r18, int r20, int r21, int r22) {
        /*
            r16 = this;
            r0 = r16
            r2 = r17
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$c r1 = r2.at
            r8 = 1
            if (r1 == 0) goto L_0x0018
            r2 = r17
            r3 = r18
            r5 = r20
            r6 = r21
            r7 = r22
            r1.sampleMetadata(r2, r3, r5, r6, r7)
            goto L_0x00d7
        L_0x0018:
            java.lang.String r1 = r2.b
            java.lang.String r3 = "S_TEXT/UTF8"
            boolean r1 = r3.equals(r1)
            java.lang.String r4 = "S_TEXT/ASS"
            if (r1 != 0) goto L_0x002c
            java.lang.String r1 = r2.b
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L_0x0048
        L_0x002c:
            int r1 = r0.ak
            java.lang.String r5 = "MatroskaExtractor"
            if (r1 <= r8) goto L_0x0038
            java.lang.String r1 = "Skipping subtitle sample in laced block."
            com.google.android.exoplayer2.util.Log.w(r5, r1)
            goto L_0x0048
        L_0x0038:
            long r6 = r0.ai
            r9 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r1 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r1 != 0) goto L_0x004b
            java.lang.String r1 = "Skipping subtitle sample with no duration."
            com.google.android.exoplayer2.util.Log.w(r5, r1)
        L_0x0048:
            r1 = r21
            goto L_0x00aa
        L_0x004b:
            java.lang.String r1 = r2.b
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r0.k
            byte[] r9 = r5.getData()
            r1.getClass()
            boolean r4 = r1.equals(r4)
            if (r4 != 0) goto L_0x0073
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x006d
            java.lang.String r1 = "%02d:%02d:%02d,%03d"
            r3 = 1000(0x3e8, double:4.94E-321)
            byte[] r1 = d(r6, r3, r1)
            r3 = 19
            goto L_0x007d
        L_0x006d:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            r1.<init>()
            throw r1
        L_0x0073:
            java.lang.String r1 = "%01d:%02d:%02d:%02d"
            r3 = 10000(0x2710, double:4.9407E-320)
            byte[] r1 = d(r6, r3, r1)
            r3 = 21
        L_0x007d:
            int r4 = r1.length
            r6 = 0
            java.lang.System.arraycopy(r1, r6, r9, r3, r4)
            int r1 = r5.getPosition()
        L_0x0086:
            int r3 = r5.limit()
            if (r1 >= r3) goto L_0x009b
            byte[] r3 = r5.getData()
            byte r3 = r3[r1]
            if (r3 != 0) goto L_0x0098
            r5.setLimit(r1)
            goto L_0x009b
        L_0x0098:
            int r1 = r1 + 1
            goto L_0x0086
        L_0x009b:
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r2.ax
            int r3 = r5.limit()
            r1.sampleData(r5, r3)
            int r1 = r5.limit()
            int r1 = r1 + r21
        L_0x00aa:
            r3 = 268435456(0x10000000, float:2.5243549E-29)
            r3 = r20 & r3
            if (r3 == 0) goto L_0x00c9
            int r3 = r0.ak
            if (r3 <= r8) goto L_0x00bc
            r3 = -268435457(0xffffffffefffffff, float:-1.5845632E29)
            r3 = r20 & r3
            r13 = r1
            r12 = r3
            goto L_0x00cc
        L_0x00bc:
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r0.n
            int r4 = r3.limit()
            com.google.android.exoplayer2.extractor.TrackOutput r5 = r2.ax
            r6 = 2
            r5.sampleData((com.google.android.exoplayer2.util.ParsableByteArray) r3, (int) r4, (int) r6)
            int r1 = r1 + r4
        L_0x00c9:
            r12 = r20
            r13 = r1
        L_0x00cc:
            com.google.android.exoplayer2.extractor.TrackOutput r9 = r2.ax
            com.google.android.exoplayer2.extractor.TrackOutput$CryptoData r15 = r2.j
            r10 = r18
            r14 = r22
            r9.sampleMetadata(r10, r12, r13, r14, r15)
        L_0x00d7:
            r0.af = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.c(com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b, long, int, int, int):void");
    }

    public final void e(ExtractorInput extractorInput, int i2) throws IOException {
        ParsableByteArray parsableByteArray = this.g;
        if (parsableByteArray.limit() < i2) {
            if (parsableByteArray.capacity() < i2) {
                parsableByteArray.ensureCapacity(Math.max(parsableByteArray.capacity() * 2, i2));
            }
            extractorInput.readFully(parsableByteArray.getData(), parsableByteArray.limit(), i2 - parsableByteArray.limit());
            parsableByteArray.setLimit(i2);
        }
    }

    public final void f() {
        this.ar = 0;
        this.as = 0;
        this.at = 0;
        this.au = false;
        this.av = false;
        this.aw = false;
        this.ax = 0;
        this.ay = 0;
        this.az = false;
        this.j.reset(0);
    }

    public final long g(long j2) throws ParserException {
        long j3 = this.r;
        if (j3 != -9223372036854775807L) {
            return Util.scaleLargeTimestamp(j2, j3, 1000);
        }
        throw new ParserException("Can't scale timecode prior to timecodeScale being set.");
    }

    @RequiresNonNull({"#2.output"})
    public final int h(int i2, ExtractorInput extractorInput, b bVar) throws IOException {
        int i3;
        int i4;
        boolean z2;
        boolean z3;
        int i5;
        if ("S_TEXT/UTF8".equals(bVar.b)) {
            i(extractorInput, bb, i2);
            int i6 = this.as;
            f();
            return i6;
        } else if ("S_TEXT/ASS".equals(bVar.b)) {
            i(extractorInput, bd, i2);
            int i7 = this.as;
            f();
            return i7;
        } else {
            TrackOutput trackOutput = bVar.ax;
            boolean z4 = this.au;
            ParsableByteArray parsableByteArray = this.j;
            boolean z5 = true;
            if (!z4) {
                boolean z6 = bVar.h;
                ParsableByteArray parsableByteArray2 = this.g;
                if (z6) {
                    this.ao &= -1073741825;
                    int i8 = 128;
                    if (!this.av) {
                        extractorInput.readFully(parsableByteArray2.getData(), 0, 1);
                        this.ar++;
                        if ((parsableByteArray2.getData()[0] & 128) != 128) {
                            this.ay = parsableByteArray2.getData()[0];
                            this.av = true;
                        } else {
                            throw new ParserException("Extension bit is set in signal byte");
                        }
                    }
                    byte b2 = this.ay;
                    if ((b2 & 1) == 1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        if ((b2 & 2) == 2) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        this.ao |= 1073741824;
                        if (!this.az) {
                            ParsableByteArray parsableByteArray3 = this.l;
                            extractorInput.readFully(parsableByteArray3.getData(), 0, 8);
                            this.ar += 8;
                            this.az = true;
                            byte[] data = parsableByteArray2.getData();
                            if (!z3) {
                                i8 = 0;
                            }
                            data[0] = (byte) (i8 | 8);
                            parsableByteArray2.setPosition(0);
                            trackOutput.sampleData(parsableByteArray2, 1, 1);
                            this.as++;
                            parsableByteArray3.setPosition(0);
                            trackOutput.sampleData(parsableByteArray3, 8, 1);
                            this.as += 8;
                        }
                        if (z3) {
                            if (!this.aw) {
                                extractorInput.readFully(parsableByteArray2.getData(), 0, 1);
                                this.ar++;
                                parsableByteArray2.setPosition(0);
                                this.ax = parsableByteArray2.readUnsignedByte();
                                this.aw = true;
                            }
                            int i9 = this.ax * 4;
                            parsableByteArray2.reset(i9);
                            extractorInput.readFully(parsableByteArray2.getData(), 0, i9);
                            this.ar += i9;
                            short s2 = (short) ((this.ax / 2) + 1);
                            int i10 = (s2 * 6) + 2;
                            ByteBuffer byteBuffer = this.o;
                            if (byteBuffer == null || byteBuffer.capacity() < i10) {
                                this.o = ByteBuffer.allocate(i10);
                            }
                            this.o.position(0);
                            this.o.putShort(s2);
                            int i11 = 0;
                            int i12 = 0;
                            while (true) {
                                i5 = this.ax;
                                if (i11 >= i5) {
                                    break;
                                }
                                int readUnsignedIntToInt = parsableByteArray2.readUnsignedIntToInt();
                                if (i11 % 2 == 0) {
                                    this.o.putShort((short) (readUnsignedIntToInt - i12));
                                } else {
                                    this.o.putInt(readUnsignedIntToInt - i12);
                                }
                                i11++;
                                i12 = readUnsignedIntToInt;
                            }
                            int i13 = (i2 - this.ar) - i12;
                            if (i5 % 2 == 1) {
                                this.o.putInt(i13);
                            } else {
                                this.o.putShort((short) i13);
                                this.o.putInt(0);
                            }
                            byte[] array = this.o.array();
                            ParsableByteArray parsableByteArray4 = this.m;
                            parsableByteArray4.reset(array, i10);
                            trackOutput.sampleData(parsableByteArray4, i10, 1);
                            this.as += i10;
                        }
                    }
                } else {
                    byte[] bArr = bVar.i;
                    if (bArr != null) {
                        parsableByteArray.reset(bArr, bArr.length);
                    }
                }
                if (bVar.f > 0) {
                    this.ao |= 268435456;
                    this.n.reset(0);
                    parsableByteArray2.reset(4);
                    parsableByteArray2.getData()[0] = (byte) ((i2 >> 24) & 255);
                    parsableByteArray2.getData()[1] = (byte) ((i2 >> 16) & 255);
                    parsableByteArray2.getData()[2] = (byte) ((i2 >> 8) & 255);
                    parsableByteArray2.getData()[3] = (byte) (i2 & 255);
                    trackOutput.sampleData(parsableByteArray2, 4, 2);
                    this.as += 4;
                }
                this.au = true;
            }
            int limit = parsableByteArray.limit() + i2;
            if (!"V_MPEG4/ISO/AVC".equals(bVar.b) && !"V_MPEGH/ISO/HEVC".equals(bVar.b)) {
                if (bVar.at != null) {
                    if (parsableByteArray.limit() != 0) {
                        z5 = false;
                    }
                    Assertions.checkState(z5);
                    bVar.at.startSample(extractorInput);
                }
                while (true) {
                    int i14 = this.ar;
                    if (i14 >= limit) {
                        break;
                    }
                    int i15 = limit - i14;
                    int bytesLeft = parsableByteArray.bytesLeft();
                    if (bytesLeft > 0) {
                        i4 = Math.min(i15, bytesLeft);
                        trackOutput.sampleData(parsableByteArray, i4);
                    } else {
                        i4 = trackOutput.sampleData((DataReader) extractorInput, i15, false);
                    }
                    this.ar += i4;
                    this.as += i4;
                }
            } else {
                ParsableByteArray parsableByteArray5 = this.f;
                byte[] data2 = parsableByteArray5.getData();
                data2[0] = 0;
                data2[1] = 0;
                data2[2] = 0;
                int i16 = bVar.ay;
                int i17 = 4 - i16;
                while (this.ar < limit) {
                    int i18 = this.at;
                    if (i18 == 0) {
                        int min = Math.min(i16, parsableByteArray.bytesLeft());
                        extractorInput.readFully(data2, i17 + min, i16 - min);
                        if (min > 0) {
                            parsableByteArray.readBytes(data2, i17, min);
                        }
                        this.ar += i16;
                        parsableByteArray5.setPosition(0);
                        this.at = parsableByteArray5.readUnsignedIntToInt();
                        ParsableByteArray parsableByteArray6 = this.e;
                        parsableByteArray6.setPosition(0);
                        trackOutput.sampleData(parsableByteArray6, 4);
                        this.as += 4;
                    } else {
                        int bytesLeft2 = parsableByteArray.bytesLeft();
                        if (bytesLeft2 > 0) {
                            i3 = Math.min(i18, bytesLeft2);
                            trackOutput.sampleData(parsableByteArray, i3);
                        } else {
                            i3 = trackOutput.sampleData((DataReader) extractorInput, i18, false);
                        }
                        this.ar += i3;
                        this.as += i3;
                        this.at -= i3;
                    }
                }
            }
            if ("A_VORBIS".equals(bVar.b)) {
                ParsableByteArray parsableByteArray7 = this.h;
                parsableByteArray7.setPosition(0);
                trackOutput.sampleData(parsableByteArray7, 4);
                this.as += 4;
            }
            int i19 = this.as;
            f();
            return i19;
        }
    }

    public final void i(ExtractorInput extractorInput, byte[] bArr, int i2) throws IOException {
        int length = bArr.length + i2;
        ParsableByteArray parsableByteArray = this.k;
        if (parsableByteArray.capacity() < length) {
            parsableByteArray.reset(Arrays.copyOf(bArr, length + i2));
        } else {
            System.arraycopy(bArr, 0, parsableByteArray.getData(), 0, bArr.length);
        }
        extractorInput.readFully(parsableByteArray.getData(), bArr.length, i2);
        parsableByteArray.setPosition(0);
        parsableByteArray.setLimit(length);
    }

    public final void init(ExtractorOutput extractorOutput) {
        this.ba = extractorOutput;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0039 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0005 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int read(com.google.android.exoplayer2.extractor.ExtractorInput r9, com.google.android.exoplayer2.extractor.PositionHolder r10) throws java.io.IOException {
        /*
            r8 = this;
            r0 = 0
            r8.af = r0
            r1 = 1
            r2 = 1
        L_0x0005:
            if (r2 == 0) goto L_0x003a
            boolean r3 = r8.af
            if (r3 != 0) goto L_0x003a
            z1 r2 = r8.a
            boolean r2 = r2.read(r9)
            if (r2 == 0) goto L_0x0005
            long r3 = r9.getPosition()
            boolean r5 = r8.y
            if (r5 == 0) goto L_0x0024
            r8.aa = r3
            long r3 = r8.z
            r10.a = r3
            r8.y = r0
            goto L_0x0034
        L_0x0024:
            boolean r3 = r8.v
            if (r3 == 0) goto L_0x0036
            long r3 = r8.aa
            r5 = -1
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x0036
            r10.a = r3
            r8.aa = r5
        L_0x0034:
            r3 = 1
            goto L_0x0037
        L_0x0036:
            r3 = 0
        L_0x0037:
            if (r3 == 0) goto L_0x0005
            return r1
        L_0x003a:
            if (r2 != 0) goto L_0x0057
        L_0x003c:
            android.util.SparseArray<com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b> r9 = r8.c
            int r10 = r9.size()
            if (r0 >= r10) goto L_0x0055
            java.lang.Object r9 = r9.valueAt(r0)
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$b r9 = (com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.b) r9
            com.google.android.exoplayer2.extractor.TrackOutput r10 = r9.ax
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r10)
            r9.outputPendingSampleMetadata()
            int r0 = r0 + 1
            goto L_0x003c
        L_0x0055:
            r9 = -1
            return r9
        L_0x0057:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public final void release() {
    }

    @CallSuper
    public void seek(long j2, long j3) {
        this.ab = -9223372036854775807L;
        int i2 = 0;
        this.ag = 0;
        this.a.reset();
        this.b.reset();
        f();
        while (true) {
            SparseArray<b> sparseArray = this.c;
            if (i2 < sparseArray.size()) {
                sparseArray.valueAt(i2).reset();
                i2++;
            } else {
                return;
            }
        }
    }

    public final boolean sniff(ExtractorInput extractorInput) throws IOException {
        return new fb().sniff(extractorInput);
    }

    public MatroskaExtractor(int i2) {
        c1 c1Var = new c1();
        this.q = -1;
        this.r = -9223372036854775807L;
        this.s = -9223372036854775807L;
        this.t = -9223372036854775807L;
        this.z = -1;
        this.aa = -1;
        this.ab = -9223372036854775807L;
        this.a = c1Var;
        c1Var.init(new a());
        this.d = (i2 & 1) == 0;
        this.b = new ld();
        this.c = new SparseArray<>();
        this.g = new ParsableByteArray(4);
        this.h = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.i = new ParsableByteArray(4);
        this.e = new ParsableByteArray(NalUnitUtil.a);
        this.f = new ParsableByteArray(4);
        this.j = new ParsableByteArray();
        this.k = new ParsableByteArray();
        this.l = new ParsableByteArray(8);
        this.m = new ParsableByteArray();
        this.n = new ParsableByteArray();
        this.al = new int[1];
    }
}
