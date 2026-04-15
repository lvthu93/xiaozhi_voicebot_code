package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

public final class H262Reader implements ElementaryStreamReader {
    public static final double[] q = {23.976023976023978d, 24.0d, 25.0d, 29.97002997002997d, 30.0d, 50.0d, 59.94005994005994d, 60.0d};
    public String a;
    public TrackOutput b;
    @Nullable
    public final a c;
    @Nullable
    public final ParsableByteArray d;
    @Nullable
    public final e7 e;
    public final boolean[] f;
    public final a g;
    public long h;
    public boolean i;
    public boolean j;
    public long k;
    public long l;
    public long m;
    public long n;
    public boolean o;
    public boolean p;

    public static final class a {
        public static final byte[] e = {0, 0, 1};
        public boolean a;
        public int b;
        public int c;
        public byte[] d;

        public a(int i) {
            this.d = new byte[i];
        }

        public void onData(byte[] bArr, int i, int i2) {
            if (this.a) {
                int i3 = i2 - i;
                byte[] bArr2 = this.d;
                int length = bArr2.length;
                int i4 = this.b;
                if (length < i4 + i3) {
                    this.d = Arrays.copyOf(bArr2, (i4 + i3) * 2);
                }
                System.arraycopy(bArr, i, this.d, this.b, i3);
                this.b += i3;
            }
        }

        public boolean onStartCode(int i, int i2) {
            if (this.a) {
                int i3 = this.b - i2;
                this.b = i3;
                if (this.c == 0 && i == 181) {
                    this.c = i3;
                } else {
                    this.a = false;
                    return true;
                }
            } else if (i == 179) {
                this.a = true;
            }
            onData(e, 0, 3);
            return false;
        }

        public void reset() {
            this.a = false;
            this.b = 0;
            this.c = 0;
        }
    }

    public H262Reader() {
        this((a) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0192  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r20) {
        /*
            r19 = this;
            r0 = r19
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r0.b
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
            int r1 = r20.getPosition()
            int r2 = r20.limit()
            byte[] r3 = r20.getData()
            long r4 = r0.h
            int r6 = r20.bytesLeft()
            long r6 = (long) r6
            long r4 = r4 + r6
            r0.h = r4
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r0.b
            int r5 = r20.bytesLeft()
            r6 = r20
            r4.sampleData(r6, r5)
        L_0x0028:
            boolean[] r4 = r0.f
            int r4 = com.google.android.exoplayer2.util.NalUnitUtil.findNalUnit(r3, r1, r2, r4)
            com.google.android.exoplayer2.extractor.ts.H262Reader$a r5 = r0.g
            e7 r7 = r0.e
            if (r4 != r2) goto L_0x0041
            boolean r4 = r0.j
            if (r4 != 0) goto L_0x003b
            r5.onData(r3, r1, r2)
        L_0x003b:
            if (r7 == 0) goto L_0x0040
            r7.appendToNalUnit(r3, r1, r2)
        L_0x0040:
            return
        L_0x0041:
            byte[] r8 = r20.getData()
            int r9 = r4 + 3
            byte r8 = r8[r9]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r10 = r4 - r1
            boolean r11 = r0.j
            if (r11 != 0) goto L_0x0137
            if (r10 <= 0) goto L_0x0056
            r5.onData(r3, r1, r4)
        L_0x0056:
            if (r10 >= 0) goto L_0x005a
            int r11 = -r10
            goto L_0x005b
        L_0x005a:
            r11 = 0
        L_0x005b:
            boolean r11 = r5.onStartCode(r8, r11)
            if (r11 == 0) goto L_0x0137
            java.lang.String r11 = r0.a
            java.lang.Object r11 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r11)
            java.lang.String r11 = (java.lang.String) r11
            byte[] r12 = r5.d
            int r13 = r5.b
            byte[] r12 = java.util.Arrays.copyOf(r12, r13)
            r13 = 4
            byte r14 = r12[r13]
            r14 = r14 & 255(0xff, float:3.57E-43)
            r16 = 5
            byte r15 = r12[r16]
            r15 = r15 & 255(0xff, float:3.57E-43)
            r17 = 6
            byte r6 = r12[r17]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r14 = r14 << r13
            int r17 = r15 >> 4
            r14 = r14 | r17
            r15 = r15 & 15
            r13 = 8
            int r15 = r15 << r13
            r6 = r6 | r15
            r15 = 7
            byte r13 = r12[r15]
            r13 = r13 & 240(0xf0, float:3.36E-43)
            r15 = 4
            int r13 = r13 >> r15
            r15 = 2
            if (r13 == r15) goto L_0x00ac
            r15 = 3
            if (r13 == r15) goto L_0x00a6
            r15 = 4
            if (r13 == r15) goto L_0x00a0
            r13 = 1065353216(0x3f800000, float:1.0)
            goto L_0x00b3
        L_0x00a0:
            int r13 = r6 * 121
            float r13 = (float) r13
            int r15 = r14 * 100
            goto L_0x00b1
        L_0x00a6:
            int r13 = r6 * 16
            float r13 = (float) r13
            int r15 = r14 * 9
            goto L_0x00b1
        L_0x00ac:
            int r13 = r6 * 4
            float r13 = (float) r13
            int r15 = r14 * 3
        L_0x00b1:
            float r15 = (float) r15
            float r13 = r13 / r15
        L_0x00b3:
            com.google.android.exoplayer2.Format$Builder r15 = new com.google.android.exoplayer2.Format$Builder
            r15.<init>()
            com.google.android.exoplayer2.Format$Builder r11 = r15.setId((java.lang.String) r11)
            java.lang.String r15 = "video/mpeg2"
            com.google.android.exoplayer2.Format$Builder r11 = r11.setSampleMimeType(r15)
            com.google.android.exoplayer2.Format$Builder r11 = r11.setWidth(r14)
            com.google.android.exoplayer2.Format$Builder r6 = r11.setHeight(r6)
            com.google.android.exoplayer2.Format$Builder r6 = r6.setPixelWidthHeightRatio(r13)
            java.util.List r11 = java.util.Collections.singletonList(r12)
            com.google.android.exoplayer2.Format$Builder r6 = r6.setInitializationData(r11)
            com.google.android.exoplayer2.Format r6 = r6.build()
            r11 = 7
            byte r11 = r12[r11]
            r11 = r11 & 15
            int r11 = r11 + -1
            if (r11 < 0) goto L_0x0113
            r13 = 8
            if (r11 >= r13) goto L_0x0113
            double[] r13 = q
            r14 = r13[r11]
            int r5 = r5.c
            int r5 = r5 + 9
            byte r5 = r12[r5]
            r11 = r5 & 96
            int r11 = r11 >> 5
            r5 = r5 & 31
            if (r11 == r5) goto L_0x0108
            double r11 = (double) r11
            r17 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r11 = r11 + r17
            int r5 = r5 + 1
            r16 = r8
            r13 = r9
            double r8 = (double) r5
            double r11 = r11 / r8
            double r14 = r14 * r11
            goto L_0x010b
        L_0x0108:
            r16 = r8
            r13 = r9
        L_0x010b:
            r8 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            double r8 = r8 / r14
            long r8 = (long) r8
            goto L_0x0118
        L_0x0113:
            r16 = r8
            r13 = r9
            r8 = 0
        L_0x0118:
            java.lang.Long r5 = java.lang.Long.valueOf(r8)
            android.util.Pair r5 = android.util.Pair.create(r6, r5)
            com.google.android.exoplayer2.extractor.TrackOutput r6 = r0.b
            java.lang.Object r8 = r5.first
            com.google.android.exoplayer2.Format r8 = (com.google.android.exoplayer2.Format) r8
            r6.format(r8)
            java.lang.Object r5 = r5.second
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            r0.k = r5
            r5 = 1
            r0.j = r5
            goto L_0x013a
        L_0x0137:
            r16 = r8
            r13 = r9
        L_0x013a:
            if (r7 == 0) goto L_0x0181
            if (r10 <= 0) goto L_0x0143
            r7.appendToNalUnit(r3, r1, r4)
            r1 = 0
            goto L_0x0144
        L_0x0143:
            int r1 = -r10
        L_0x0144:
            boolean r1 = r7.endNalUnit(r1)
            if (r1 == 0) goto L_0x016c
            byte[] r1 = r7.d
            int r5 = r7.e
            int r1 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r1, r5)
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r0.d
            java.lang.Object r6 = com.google.android.exoplayer2.util.Util.castNonNull(r5)
            com.google.android.exoplayer2.util.ParsableByteArray r6 = (com.google.android.exoplayer2.util.ParsableByteArray) r6
            byte[] r8 = r7.d
            r6.reset(r8, r1)
            com.google.android.exoplayer2.extractor.ts.a r1 = r0.c
            java.lang.Object r1 = com.google.android.exoplayer2.util.Util.castNonNull(r1)
            com.google.android.exoplayer2.extractor.ts.a r1 = (com.google.android.exoplayer2.extractor.ts.a) r1
            long r8 = r0.n
            r1.consume(r8, r5)
        L_0x016c:
            r1 = 178(0xb2, float:2.5E-43)
            r5 = r16
            if (r5 != r1) goto L_0x0183
            byte[] r1 = r20.getData()
            int r6 = r4 + 2
            byte r1 = r1[r6]
            r6 = 1
            if (r1 != r6) goto L_0x0183
            r7.startNalUnit(r5)
            goto L_0x0183
        L_0x0181:
            r5 = r16
        L_0x0183:
            if (r5 == 0) goto L_0x0192
            r1 = 179(0xb3, float:2.51E-43)
            if (r5 != r1) goto L_0x018a
            goto L_0x0192
        L_0x018a:
            r1 = 184(0xb8, float:2.58E-43)
            if (r5 != r1) goto L_0x01ec
            r1 = 1
            r0.o = r1
            goto L_0x01ec
        L_0x0192:
            int r1 = r2 - r4
            boolean r4 = r0.i
            if (r4 == 0) goto L_0x01b3
            boolean r4 = r0.p
            if (r4 == 0) goto L_0x01b3
            boolean r4 = r0.j
            if (r4 == 0) goto L_0x01b3
            boolean r9 = r0.o
            long r6 = r0.h
            long r10 = r0.m
            long r6 = r6 - r10
            int r4 = (int) r6
            int r10 = r4 - r1
            com.google.android.exoplayer2.extractor.TrackOutput r6 = r0.b
            long r7 = r0.n
            r12 = 0
            r11 = r1
            r6.sampleMetadata(r7, r9, r10, r11, r12)
        L_0x01b3:
            boolean r4 = r0.i
            if (r4 == 0) goto L_0x01bf
            boolean r6 = r0.p
            if (r6 == 0) goto L_0x01bc
            goto L_0x01bf
        L_0x01bc:
            r1 = 0
            r4 = 1
            goto L_0x01e5
        L_0x01bf:
            long r6 = r0.h
            long r8 = (long) r1
            long r6 = r6 - r8
            r0.m = r6
            long r6 = r0.l
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 == 0) goto L_0x01d1
            goto L_0x01db
        L_0x01d1:
            if (r4 == 0) goto L_0x01d9
            long r6 = r0.n
            long r10 = r0.k
            long r6 = r6 + r10
            goto L_0x01db
        L_0x01d9:
            r6 = 0
        L_0x01db:
            r0.n = r6
            r1 = 0
            r0.o = r1
            r0.l = r8
            r4 = 1
            r0.i = r4
        L_0x01e5:
            if (r5 != 0) goto L_0x01e9
            r14 = 1
            goto L_0x01ea
        L_0x01e9:
            r14 = 0
        L_0x01ea:
            r0.p = r14
        L_0x01ec:
            r6 = r20
            r1 = r13
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H262Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.a = trackIdGenerator.getFormatId();
        this.b = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        a aVar = this.c;
        if (aVar != null) {
            aVar.createTracks(extractorOutput, trackIdGenerator);
        }
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.l = j2;
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.f);
        this.g.reset();
        e7 e7Var = this.e;
        if (e7Var != null) {
            e7Var.reset();
        }
        this.h = 0;
        this.i = false;
    }

    public H262Reader(@Nullable a aVar) {
        this.c = aVar;
        this.f = new boolean[4];
        this.g = new a(128);
        if (aVar != null) {
            this.e = new e7(178, 128);
            this.d = new ParsableByteArray();
            return;
        }
        this.e = null;
        this.d = null;
    }
}
