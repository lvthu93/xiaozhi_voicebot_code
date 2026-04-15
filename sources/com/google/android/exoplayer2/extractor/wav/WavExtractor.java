package com.google.android.exoplayer2.extractor.wav;

import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import okhttp3.internal.http.StatusLine;
import org.mozilla.javascript.Token;

public final class WavExtractor implements Extractor {
    public ExtractorOutput a;
    public TrackOutput b;
    public b c;
    public int d = -1;
    public long e = -1;

    public static final class a implements b {
        public static final int[] m = {-1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8};
        public static final int[] n = {7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 21, 23, 25, 28, 31, 34, 37, 41, 45, 50, 55, 60, 66, 73, 80, 88, 97, 107, 118, 130, Token.SET_REF_OP, Token.SETCONSTVAR, 173, 190, 209, 230, 253, 279, StatusLine.HTTP_TEMP_REDIRECT, 337, 371, 408, 449, 494, 544, 598, 658, 724, 796, 876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066, 2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358, 5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899, 15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767};
        public final ExtractorOutput a;
        public final TrackOutput b;
        public final fe c;
        public final int d;
        public final byte[] e;
        public final ParsableByteArray f;
        public final int g;
        public final Format h;
        public int i;
        public long j;
        public int k;
        public long l;

        public a(ExtractorOutput extractorOutput, TrackOutput trackOutput, fe feVar) throws ParserException {
            this.a = extractorOutput;
            this.b = trackOutput;
            this.c = feVar;
            int max = Math.max(1, feVar.c / 10);
            this.g = max;
            ParsableByteArray parsableByteArray = new ParsableByteArray(feVar.f);
            parsableByteArray.readLittleEndianUnsignedShort();
            int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
            this.d = readLittleEndianUnsignedShort;
            int i2 = feVar.b;
            int i3 = feVar.d;
            int i4 = (((i3 - (i2 * 4)) * 8) / (feVar.e * i2)) + 1;
            if (readLittleEndianUnsignedShort == i4) {
                int ceilDivide = Util.ceilDivide(max, readLittleEndianUnsignedShort);
                this.e = new byte[(ceilDivide * i3)];
                this.f = new ParsableByteArray(readLittleEndianUnsignedShort * 2 * i2 * ceilDivide);
                int i5 = feVar.c;
                int i6 = ((i3 * i5) * 8) / readLittleEndianUnsignedShort;
                this.h = new Format.Builder().setSampleMimeType("audio/raw").setAverageBitrate(i6).setPeakBitrate(i6).setMaxInputSize(max * 2 * i2).setChannelCount(i2).setSampleRate(i5).setPcmEncoding(2).build();
                return;
            }
            StringBuilder sb = new StringBuilder(56);
            sb.append("Expected frames per block: ");
            sb.append(i4);
            sb.append("; got: ");
            sb.append(readLittleEndianUnsignedShort);
            throw new ParserException(sb.toString());
        }

        public final void a(int i2) {
            int i3 = i2;
            long j2 = this.j;
            long j3 = this.l;
            fe feVar = this.c;
            long scaleLargeTimestamp = j2 + Util.scaleLargeTimestamp(j3, 1000000, (long) feVar.c);
            int i4 = i3 * 2 * feVar.b;
            this.b.sampleMetadata(scaleLargeTimestamp, 1, i4, this.k - i4, (TrackOutput.CryptoData) null);
            this.l += (long) i3;
            this.k -= i4;
        }

        public void init(int i2, long j2) {
            this.a.seekMap(new he(this.c, this.d, (long) i2, j2));
            this.b.format(this.h);
        }

        public void reset(long j2) {
            this.i = 0;
            this.j = j2;
            this.k = 0;
            this.l = 0;
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0051 A[EDGE_INSN: B:42:0x0051->B:12:0x0051 ?: BREAK  
        EDGE_INSN: B:43:0x0051->B:12:0x0051 ?: BREAK  ] */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0038  */
        public boolean sampleData(com.google.android.exoplayer2.extractor.ExtractorInput r25, long r26) throws java.io.IOException {
            /*
                r24 = this;
                r0 = r24
                int r1 = r0.k
                fe r2 = r0.c
                int r3 = r2.b
                int r3 = r3 * 2
                int r1 = r1 / r3
                int r3 = r0.g
                int r1 = r3 - r1
                int r4 = r0.d
                int r1 = com.google.android.exoplayer2.util.Util.ceilDivide((int) r1, (int) r4)
                int r5 = r2.d
                int r1 = r1 * r5
                r5 = 0
                int r9 = (r26 > r5 ? 1 : (r26 == r5 ? 0 : -1))
                r10 = r0
                r5 = r2
                r6 = r3
                if (r9 != 0) goto L_0x0029
                r9 = r4
                r2 = r26
                r4 = r1
                r1 = r25
                goto L_0x0049
            L_0x0029:
                r9 = r4
                r11 = 0
                r2 = r26
                r4 = r1
                r1 = r25
            L_0x0030:
                byte[] r12 = r10.e
                if (r11 != 0) goto L_0x0051
                int r13 = r10.i
                if (r13 >= r4) goto L_0x0051
                int r13 = r4 - r13
                long r13 = (long) r13
                long r13 = java.lang.Math.min(r13, r2)
                int r14 = (int) r13
                int r13 = r10.i
                int r12 = r1.read(r12, r13, r14)
                r13 = -1
                if (r12 != r13) goto L_0x004b
            L_0x0049:
                r11 = 1
                goto L_0x0030
            L_0x004b:
                int r13 = r10.i
                int r13 = r13 + r12
                r10.i = r13
                goto L_0x0030
            L_0x0051:
                int r1 = r10.i
                int r2 = r5.d
                int r1 = r1 / r2
                fe r2 = r10.c
                if (r1 <= 0) goto L_0x015e
                r3 = 0
            L_0x005b:
                int r4 = r5.d
                com.google.android.exoplayer2.util.ParsableByteArray r13 = r10.f
                if (r3 >= r1) goto L_0x012d
                r14 = 0
            L_0x0062:
                int r15 = r5.b
                if (r14 >= r15) goto L_0x011f
                byte[] r16 = r13.getData()
                int r17 = r3 * r4
                int r18 = r14 * 4
                int r18 = r18 + r17
                int r17 = r15 * 4
                int r17 = r17 + r18
                int r19 = r4 / r15
                int r19 = r19 + -4
                int r20 = r18 + 1
                byte r7 = r12[r20]
                r7 = r7 & 255(0xff, float:3.57E-43)
                int r7 = r7 << 8
                byte r8 = r12[r18]
                r8 = r8 & 255(0xff, float:3.57E-43)
                r7 = r7 | r8
                short r7 = (short) r7
                int r18 = r18 + 2
                byte r8 = r12[r18]
                r8 = r8 & 255(0xff, float:3.57E-43)
                r0 = 88
                int r8 = java.lang.Math.min(r8, r0)
                int[] r18 = n
                r21 = r18[r8]
                int r22 = r3 * r9
                int r22 = r22 * r15
                int r22 = r22 + r14
                int r22 = r22 * 2
                r0 = r7 & 255(0xff, float:3.57E-43)
                byte r0 = (byte) r0
                r16[r22] = r0
                int r0 = r22 + 1
                r26 = r5
                int r5 = r7 >> 8
                byte r5 = (byte) r5
                r16[r0] = r5
                r0 = 0
            L_0x00ad:
                int r5 = r19 * 2
                if (r0 >= r5) goto L_0x0111
                int r5 = r0 / 8
                int r23 = r0 / 2
                int r23 = r23 % 4
                int r5 = r5 * r15
                int r5 = r5 * 4
                int r5 = r5 + r17
                int r5 = r5 + r23
                byte r5 = r12[r5]
                r5 = r5 & 255(0xff, float:3.57E-43)
                int r23 = r0 % 2
                if (r23 != 0) goto L_0x00ca
                r5 = r5 & 15
                goto L_0x00cc
            L_0x00ca:
                int r5 = r5 >> 4
            L_0x00cc:
                r23 = r5 & 7
                int r23 = r23 * 2
                r20 = 1
                int r23 = r23 + 1
                int r23 = r23 * r21
                r21 = r12
                int r12 = r23 >> 3
                r23 = r5 & 8
                if (r23 == 0) goto L_0x00df
                int r12 = -r12
            L_0x00df:
                int r7 = r7 + r12
                r12 = -32768(0xffffffffffff8000, float:NaN)
                r27 = r11
                r11 = 32767(0x7fff, float:4.5916E-41)
                int r7 = com.google.android.exoplayer2.util.Util.constrainValue((int) r7, (int) r12, (int) r11)
                int r11 = r15 * 2
                int r22 = r11 + r22
                r11 = r7 & 255(0xff, float:3.57E-43)
                byte r11 = (byte) r11
                r16[r22] = r11
                int r11 = r22 + 1
                int r12 = r7 >> 8
                byte r12 = (byte) r12
                r16[r11] = r12
                int[] r11 = m
                r5 = r11[r5]
                int r8 = r8 + r5
                r5 = 88
                r11 = 0
                int r8 = com.google.android.exoplayer2.util.Util.constrainValue((int) r8, (int) r11, (int) r5)
                r11 = r18[r8]
                int r0 = r0 + 1
                r12 = r21
                r21 = r11
                r11 = r27
                goto L_0x00ad
            L_0x0111:
                r27 = r11
                r21 = r12
                r20 = 1
                int r14 = r14 + 1
                r0 = r24
                r5 = r26
                goto L_0x0062
            L_0x011f:
                r26 = r5
                r27 = r11
                r21 = r12
                r20 = 1
                int r3 = r3 + 1
                r0 = r24
                goto L_0x005b
            L_0x012d:
                r27 = r11
                int r9 = r9 * r1
                int r0 = r2.b
                int r9 = r9 * 2
                int r9 = r9 * r0
                r0 = 0
                r13.setPosition(r0)
                r13.setLimit(r9)
                int r0 = r10.i
                int r1 = r1 * r4
                int r0 = r0 - r1
                r10.i = r0
                int r0 = r13.limit()
                com.google.android.exoplayer2.extractor.TrackOutput r1 = r10.b
                r1.sampleData(r13, r0)
                int r1 = r10.k
                int r1 = r1 + r0
                r10.k = r1
                int r0 = r2.b
                int r0 = r0 * 2
                int r1 = r1 / r0
                if (r1 < r6) goto L_0x0160
                r10.a(r6)
                goto L_0x0160
            L_0x015e:
                r27 = r11
            L_0x0160:
                if (r27 == 0) goto L_0x016e
                int r0 = r10.k
                int r1 = r2.b
                int r1 = r1 * 2
                int r0 = r0 / r1
                if (r0 <= 0) goto L_0x016e
                r10.a(r0)
            L_0x016e:
                return r27
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.wav.WavExtractor.a.sampleData(com.google.android.exoplayer2.extractor.ExtractorInput, long):boolean");
        }
    }

    public interface b {
        void init(int i, long j) throws ParserException;

        void reset(long j);

        boolean sampleData(ExtractorInput extractorInput, long j) throws IOException;
    }

    public static final class c implements b {
        public final ExtractorOutput a;
        public final TrackOutput b;
        public final fe c;
        public final Format d;
        public final int e;
        public long f;
        public int g;
        public long h;

        public c(ExtractorOutput extractorOutput, TrackOutput trackOutput, fe feVar, String str, int i) throws ParserException {
            this.a = extractorOutput;
            this.b = trackOutput;
            this.c = feVar;
            int i2 = (feVar.b * feVar.e) / 8;
            int i3 = feVar.d;
            if (i3 == i2) {
                int i4 = feVar.c;
                int i5 = i4 * i2 * 8;
                int max = Math.max(i2, (i4 * i2) / 10);
                this.e = max;
                this.d = new Format.Builder().setSampleMimeType(str).setAverageBitrate(i5).setPeakBitrate(i5).setMaxInputSize(max).setChannelCount(feVar.b).setSampleRate(i4).setPcmEncoding(i).build();
                return;
            }
            StringBuilder sb = new StringBuilder(50);
            sb.append("Expected block size: ");
            sb.append(i2);
            sb.append("; got: ");
            sb.append(i3);
            throw new ParserException(sb.toString());
        }

        public void init(int i, long j) {
            this.a.seekMap(new he(this.c, 1, (long) i, j));
            this.b.format(this.d);
        }

        public void reset(long j) {
            this.f = j;
            this.g = 0;
            this.h = 0;
        }

        public boolean sampleData(ExtractorInput extractorInput, long j) throws IOException {
            int i;
            int i2;
            int i3;
            long j2 = j;
            while (true) {
                i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
                if (i <= 0 || (i2 = this.g) >= (i3 = this.e)) {
                    fe feVar = this.c;
                    int i4 = feVar.d;
                    int i5 = this.g / i4;
                } else {
                    int sampleData = this.b.sampleData((DataReader) extractorInput, (int) Math.min((long) (i3 - i2), j2), true);
                    if (sampleData == -1) {
                        j2 = 0;
                    } else {
                        this.g += sampleData;
                        j2 -= (long) sampleData;
                    }
                }
            }
            fe feVar2 = this.c;
            int i42 = feVar2.d;
            int i52 = this.g / i42;
            if (i52 > 0) {
                int i6 = i52 * i42;
                int i7 = this.g - i6;
                this.b.sampleMetadata(this.f + Util.scaleLargeTimestamp(this.h, 1000000, (long) feVar2.c), 1, i6, i7, (TrackOutput.CryptoData) null);
                this.h += (long) i52;
                this.g = i7;
            }
            if (i <= 0) {
                return true;
            }
            return false;
        }
    }

    public void init(ExtractorOutput extractorOutput) {
        this.a = extractorOutput;
        this.b = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        Assertions.checkStateNotNull(this.b);
        Util.castNonNull(this.a);
        if (this.c == null) {
            fe peek = ge.peek(extractorInput);
            if (peek != null) {
                int i = peek.a;
                if (i == 17) {
                    this.c = new a(this.a, this.b, peek);
                } else if (i == 6) {
                    this.c = new c(this.a, this.b, peek, "audio/g711-alaw", -1);
                } else if (i == 7) {
                    this.c = new c(this.a, this.b, peek, "audio/g711-mlaw", -1);
                } else {
                    int pcmEncodingForType = WavUtil.getPcmEncodingForType(i, peek.e);
                    if (pcmEncodingForType != 0) {
                        this.c = new c(this.a, this.b, peek, "audio/raw", pcmEncodingForType);
                    } else {
                        throw new ParserException(y2.d(40, "Unsupported WAV format type: ", i));
                    }
                }
            } else {
                throw new ParserException("Unsupported or unrecognized wav header.");
            }
        }
        if (this.d == -1) {
            Pair<Long, Long> skipToData = ge.skipToData(extractorInput);
            this.d = ((Long) skipToData.first).intValue();
            long longValue = ((Long) skipToData.second).longValue();
            this.e = longValue;
            this.c.init(this.d, longValue);
        } else if (extractorInput.getPosition() == 0) {
            extractorInput.skipFully(this.d);
        }
        if (this.e != -1) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (this.c.sampleData(extractorInput, this.e - extractorInput.getPosition())) {
            return -1;
        }
        return 0;
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        b bVar = this.c;
        if (bVar != null) {
            bVar.reset(j2);
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return ge.peek(extractorInput) != null;
    }
}
