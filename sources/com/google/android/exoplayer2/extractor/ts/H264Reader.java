package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class H264Reader implements ElementaryStreamReader {
    public final SeiReader a;
    public final boolean b;
    public final boolean c;
    public final e7 d = new e7(7, 128);
    public final e7 e = new e7(8, 128);
    public final e7 f = new e7(6, 128);
    public long g;
    public final boolean[] h = new boolean[3];
    public String i;
    public TrackOutput j;
    public a k;
    public boolean l;
    public long m;
    public boolean n;
    public final ParsableByteArray o = new ParsableByteArray();

    public static final class a {
        public final TrackOutput a;
        public final boolean b;
        public final boolean c;
        public final SparseArray<NalUnitUtil.SpsData> d = new SparseArray<>();
        public final SparseArray<NalUnitUtil.PpsData> e = new SparseArray<>();
        public final ParsableNalUnitBitArray f;
        public byte[] g;
        public int h;
        public int i;
        public long j;
        public boolean k;
        public long l;
        public C0016a m = new C0016a();
        public C0016a n = new C0016a();
        public boolean o;
        public long p;
        public long q;
        public boolean r;

        /* renamed from: com.google.android.exoplayer2.extractor.ts.H264Reader$a$a  reason: collision with other inner class name */
        public static final class C0016a {
            public boolean a;
            public boolean b;
            @Nullable
            public NalUnitUtil.SpsData c;
            public int d;
            public int e;
            public int f;
            public int g;
            public boolean h;
            public boolean i;
            public boolean j;
            public boolean k;
            public int l;
            public int m;
            public int n;
            public int o;
            public int p;

            public void clear() {
                this.b = false;
                this.a = false;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
                r0 = r2.e;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean isISlice() {
                /*
                    r2 = this;
                    boolean r0 = r2.b
                    if (r0 == 0) goto L_0x000e
                    int r0 = r2.e
                    r1 = 7
                    if (r0 == r1) goto L_0x000c
                    r1 = 2
                    if (r0 != r1) goto L_0x000e
                L_0x000c:
                    r0 = 1
                    goto L_0x000f
                L_0x000e:
                    r0 = 0
                L_0x000f:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H264Reader.a.C0016a.isISlice():boolean");
            }

            public void setAll(NalUnitUtil.SpsData spsData, int i2, int i3, int i4, int i5, boolean z, boolean z2, boolean z3, boolean z4, int i6, int i7, int i8, int i9, int i10) {
                this.c = spsData;
                this.d = i2;
                this.e = i3;
                this.f = i4;
                this.g = i5;
                this.h = z;
                this.i = z2;
                this.j = z3;
                this.k = z4;
                this.l = i6;
                this.m = i7;
                this.n = i8;
                this.o = i9;
                this.p = i10;
                this.a = true;
                this.b = true;
            }

            public void setSliceType(int i2) {
                this.e = i2;
                this.b = true;
            }
        }

        public a(TrackOutput trackOutput, boolean z, boolean z2) {
            this.a = trackOutput;
            this.b = z;
            this.c = z2;
            byte[] bArr = new byte[128];
            this.g = bArr;
            this.f = new ParsableNalUnitBitArray(bArr, 0, 0);
            reset();
        }

        /* JADX WARNING: Removed duplicated region for block: B:51:0x00d7  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00da  */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00de  */
        /* JADX WARNING: Removed duplicated region for block: B:58:0x00ec  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x00f4  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x0118  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void appendToNalUnit(byte[] r24, int r25, int r26) {
            /*
                r23 = this;
                r0 = r23
                r1 = r25
                boolean r2 = r0.k
                if (r2 != 0) goto L_0x0009
                return
            L_0x0009:
                int r2 = r26 - r1
                byte[] r3 = r0.g
                int r4 = r3.length
                int r5 = r0.h
                int r6 = r5 + r2
                r7 = 2
                if (r4 >= r6) goto L_0x001e
                int r5 = r5 + r2
                int r5 = r5 * 2
                byte[] r3 = java.util.Arrays.copyOf(r3, r5)
                r0.g = r3
            L_0x001e:
                byte[] r3 = r0.g
                int r4 = r0.h
                r5 = r24
                java.lang.System.arraycopy(r5, r1, r3, r4, r2)
                int r1 = r0.h
                int r1 = r1 + r2
                r0.h = r1
                byte[] r2 = r0.g
                com.google.android.exoplayer2.util.ParsableNalUnitBitArray r3 = r0.f
                r4 = 0
                r3.reset(r2, r4, r1)
                r1 = 8
                boolean r1 = r3.canReadBits(r1)
                if (r1 != 0) goto L_0x003d
                return
            L_0x003d:
                r3.skipBit()
                int r10 = r3.readBits(r7)
                r1 = 5
                r3.skipBits(r1)
                boolean r2 = r3.canReadExpGolombCodedNum()
                if (r2 != 0) goto L_0x004f
                return
            L_0x004f:
                r3.readUnsignedExpGolombCodedInt()
                boolean r2 = r3.canReadExpGolombCodedNum()
                if (r2 != 0) goto L_0x0059
                return
            L_0x0059:
                int r11 = r3.readUnsignedExpGolombCodedInt()
                boolean r2 = r0.c
                if (r2 != 0) goto L_0x0069
                r0.k = r4
                com.google.android.exoplayer2.extractor.ts.H264Reader$a$a r1 = r0.n
                r1.setSliceType(r11)
                return
            L_0x0069:
                boolean r2 = r3.canReadExpGolombCodedNum()
                if (r2 != 0) goto L_0x0070
                return
            L_0x0070:
                int r13 = r3.readUnsignedExpGolombCodedInt()
                android.util.SparseArray<com.google.android.exoplayer2.util.NalUnitUtil$PpsData> r2 = r0.e
                int r5 = r2.indexOfKey(r13)
                if (r5 >= 0) goto L_0x007f
                r0.k = r4
                return
            L_0x007f:
                java.lang.Object r2 = r2.get(r13)
                com.google.android.exoplayer2.util.NalUnitUtil$PpsData r2 = (com.google.android.exoplayer2.util.NalUnitUtil.PpsData) r2
                android.util.SparseArray<com.google.android.exoplayer2.util.NalUnitUtil$SpsData> r5 = r0.d
                int r6 = r2.b
                java.lang.Object r5 = r5.get(r6)
                r9 = r5
                com.google.android.exoplayer2.util.NalUnitUtil$SpsData r9 = (com.google.android.exoplayer2.util.NalUnitUtil.SpsData) r9
                boolean r5 = r9.h
                if (r5 == 0) goto L_0x009e
                boolean r5 = r3.canReadBits(r7)
                if (r5 != 0) goto L_0x009b
                return
            L_0x009b:
                r3.skipBits(r7)
            L_0x009e:
                int r5 = r9.j
                boolean r6 = r3.canReadBits(r5)
                if (r6 != 0) goto L_0x00a7
                return
            L_0x00a7:
                int r12 = r3.readBits(r5)
                boolean r5 = r9.i
                r6 = 1
                if (r5 != 0) goto L_0x00cf
                boolean r5 = r3.canReadBits(r6)
                if (r5 != 0) goto L_0x00b7
                return
            L_0x00b7:
                boolean r5 = r3.readBit()
                if (r5 == 0) goto L_0x00cd
                boolean r7 = r3.canReadBits(r6)
                if (r7 != 0) goto L_0x00c4
                return
            L_0x00c4:
                boolean r7 = r3.readBit()
                r14 = r5
                r16 = r7
                r15 = 1
                goto L_0x00d3
            L_0x00cd:
                r14 = r5
                goto L_0x00d0
            L_0x00cf:
                r14 = 0
            L_0x00d0:
                r15 = 0
                r16 = 0
            L_0x00d3:
                int r5 = r0.i
                if (r5 != r1) goto L_0x00da
                r17 = 1
                goto L_0x00dc
            L_0x00da:
                r17 = 0
            L_0x00dc:
                if (r17 == 0) goto L_0x00ec
                boolean r1 = r3.canReadExpGolombCodedNum()
                if (r1 != 0) goto L_0x00e5
                return
            L_0x00e5:
                int r1 = r3.readUnsignedExpGolombCodedInt()
                r18 = r1
                goto L_0x00ee
            L_0x00ec:
                r18 = 0
            L_0x00ee:
                boolean r1 = r2.c
                int r2 = r9.k
                if (r2 != 0) goto L_0x0118
                int r2 = r9.l
                boolean r5 = r3.canReadBits(r2)
                if (r5 != 0) goto L_0x00fd
                return
            L_0x00fd:
                int r2 = r3.readBits(r2)
                if (r1 == 0) goto L_0x0115
                if (r14 != 0) goto L_0x0115
                boolean r1 = r3.canReadExpGolombCodedNum()
                if (r1 != 0) goto L_0x010c
                return
            L_0x010c:
                int r1 = r3.readSignedExpGolombCodedInt()
                r20 = r1
                r19 = r2
                goto L_0x014c
            L_0x0115:
                r19 = r2
                goto L_0x014a
            L_0x0118:
                if (r2 != r6) goto L_0x0148
                boolean r2 = r9.m
                if (r2 != 0) goto L_0x0148
                boolean r2 = r3.canReadExpGolombCodedNum()
                if (r2 != 0) goto L_0x0125
                return
            L_0x0125:
                int r2 = r3.readSignedExpGolombCodedInt()
                if (r1 == 0) goto L_0x0141
                if (r14 != 0) goto L_0x0141
                boolean r1 = r3.canReadExpGolombCodedNum()
                if (r1 != 0) goto L_0x0134
                return
            L_0x0134:
                int r1 = r3.readSignedExpGolombCodedInt()
                r22 = r1
                r21 = r2
                r19 = 0
                r20 = 0
                goto L_0x0150
            L_0x0141:
                r21 = r2
                r19 = 0
                r20 = 0
                goto L_0x014e
            L_0x0148:
                r19 = 0
            L_0x014a:
                r20 = 0
            L_0x014c:
                r21 = 0
            L_0x014e:
                r22 = 0
            L_0x0150:
                com.google.android.exoplayer2.extractor.ts.H264Reader$a$a r8 = r0.n
                r8.setAll(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
                r0.k = r4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H264Reader.a.appendToNalUnit(byte[], int, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x004b, code lost:
            if (r1.j == r2.j) goto L_0x004d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0055, code lost:
            if (r8 != 0) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0069, code lost:
            if (r1.n == r2.n) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x007b, code lost:
            if (r1.p == r2.p) goto L_0x007d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0089, code lost:
            if (r1.l == r2.l) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x008f, code lost:
            if (r1 == false) goto L_0x00b8;
         */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00bc  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x00c3  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean endNalUnit(long r15, int r17, boolean r18, boolean r19) {
            /*
                r14 = this;
                r0 = r14
                int r1 = r0.i
                r2 = 9
                r3 = 0
                r4 = 1
                if (r1 == r2) goto L_0x0091
                boolean r1 = r0.c
                if (r1 == 0) goto L_0x00b8
                com.google.android.exoplayer2.extractor.ts.H264Reader$a$a r1 = r0.n
                com.google.android.exoplayer2.extractor.ts.H264Reader$a$a r2 = r0.m
                boolean r5 = r1.a
                if (r5 != 0) goto L_0x0017
                goto L_0x008c
            L_0x0017:
                boolean r5 = r2.a
                if (r5 != 0) goto L_0x001d
                goto L_0x008e
            L_0x001d:
                com.google.android.exoplayer2.util.NalUnitUtil$SpsData r5 = r1.c
                java.lang.Object r5 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r5)
                com.google.android.exoplayer2.util.NalUnitUtil$SpsData r5 = (com.google.android.exoplayer2.util.NalUnitUtil.SpsData) r5
                com.google.android.exoplayer2.util.NalUnitUtil$SpsData r6 = r2.c
                java.lang.Object r6 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r6)
                com.google.android.exoplayer2.util.NalUnitUtil$SpsData r6 = (com.google.android.exoplayer2.util.NalUnitUtil.SpsData) r6
                int r7 = r1.f
                int r8 = r2.f
                if (r7 != r8) goto L_0x008e
                int r7 = r1.g
                int r8 = r2.g
                if (r7 != r8) goto L_0x008e
                boolean r7 = r1.h
                boolean r8 = r2.h
                if (r7 != r8) goto L_0x008e
                boolean r7 = r1.i
                if (r7 == 0) goto L_0x004d
                boolean r7 = r2.i
                if (r7 == 0) goto L_0x004d
                boolean r7 = r1.j
                boolean r8 = r2.j
                if (r7 != r8) goto L_0x008e
            L_0x004d:
                int r7 = r1.d
                int r8 = r2.d
                if (r7 == r8) goto L_0x0057
                if (r7 == 0) goto L_0x008e
                if (r8 == 0) goto L_0x008e
            L_0x0057:
                int r5 = r5.k
                if (r5 != 0) goto L_0x006b
                int r7 = r6.k
                if (r7 != 0) goto L_0x006b
                int r7 = r1.m
                int r8 = r2.m
                if (r7 != r8) goto L_0x008e
                int r7 = r1.n
                int r8 = r2.n
                if (r7 != r8) goto L_0x008e
            L_0x006b:
                if (r5 != r4) goto L_0x007d
                int r5 = r6.k
                if (r5 != r4) goto L_0x007d
                int r5 = r1.o
                int r6 = r2.o
                if (r5 != r6) goto L_0x008e
                int r5 = r1.p
                int r6 = r2.p
                if (r5 != r6) goto L_0x008e
            L_0x007d:
                boolean r5 = r1.k
                boolean r6 = r2.k
                if (r5 != r6) goto L_0x008e
                if (r5 == 0) goto L_0x008c
                int r1 = r1.l
                int r2 = r2.l
                if (r1 == r2) goto L_0x008c
                goto L_0x008e
            L_0x008c:
                r1 = 0
                goto L_0x008f
            L_0x008e:
                r1 = 1
            L_0x008f:
                if (r1 == 0) goto L_0x00b8
            L_0x0091:
                if (r18 == 0) goto L_0x00ac
                boolean r1 = r0.o
                if (r1 == 0) goto L_0x00ac
                long r1 = r0.j
                long r5 = r15 - r1
                int r6 = (int) r5
                int r12 = r17 + r6
                boolean r10 = r0.r
                long r5 = r0.p
                long r1 = r1 - r5
                int r11 = (int) r1
                com.google.android.exoplayer2.extractor.TrackOutput r7 = r0.a
                long r8 = r0.q
                r13 = 0
                r7.sampleMetadata(r8, r10, r11, r12, r13)
            L_0x00ac:
                long r1 = r0.j
                r0.p = r1
                long r1 = r0.l
                r0.q = r1
                r0.r = r3
                r0.o = r4
            L_0x00b8:
                boolean r1 = r0.b
                if (r1 == 0) goto L_0x00c3
                com.google.android.exoplayer2.extractor.ts.H264Reader$a$a r1 = r0.n
                boolean r1 = r1.isISlice()
                goto L_0x00c5
            L_0x00c3:
                r1 = r19
            L_0x00c5:
                boolean r2 = r0.r
                int r5 = r0.i
                r6 = 5
                if (r5 == r6) goto L_0x00d0
                if (r1 == 0) goto L_0x00d1
                if (r5 != r4) goto L_0x00d1
            L_0x00d0:
                r3 = 1
            L_0x00d1:
                r1 = r2 | r3
                r0.r = r1
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H264Reader.a.endNalUnit(long, int, boolean, boolean):boolean");
        }

        public boolean needsSpsPps() {
            return this.c;
        }

        public void putPps(NalUnitUtil.PpsData ppsData) {
            this.e.append(ppsData.a, ppsData);
        }

        public void putSps(NalUnitUtil.SpsData spsData) {
            this.d.append(spsData.d, spsData);
        }

        public void reset() {
            this.k = false;
            this.o = false;
            this.n.clear();
        }

        public void startNalUnit(long j2, int i2, long j3) {
            this.i = i2;
            this.l = j3;
            this.j = j2;
            if (!this.b || i2 != 1) {
                if (!this.c) {
                    return;
                }
                if (!(i2 == 5 || i2 == 1 || i2 == 2)) {
                    return;
                }
            }
            C0016a aVar = this.m;
            this.m = this.n;
            this.n = aVar;
            aVar.clear();
            this.h = 0;
            this.k = true;
        }
    }

    public H264Reader(SeiReader seiReader, boolean z, boolean z2) {
        this.a = seiReader;
        this.b = z;
        this.c = z2;
    }

    @RequiresNonNull({"sampleReader"})
    public final void a(byte[] bArr, int i2, int i3) {
        if (!this.l || this.k.needsSpsPps()) {
            this.d.appendToNalUnit(bArr, i2, i3);
            this.e.appendToNalUnit(bArr, i2, i3);
        }
        this.f.appendToNalUnit(bArr, i2, i3);
        this.k.appendToNalUnit(bArr, i2, i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x016e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0183  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r21) {
        /*
            r20 = this;
            r0 = r20
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r0.j
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r1 = r0.k
            com.google.android.exoplayer2.util.Util.castNonNull(r1)
            int r1 = r21.getPosition()
            int r2 = r21.limit()
            byte[] r3 = r21.getData()
            long r4 = r0.g
            int r6 = r21.bytesLeft()
            long r6 = (long) r6
            long r4 = r4 + r6
            r0.g = r4
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r0.j
            int r5 = r21.bytesLeft()
            r6 = r21
            r4.sampleData(r6, r5)
        L_0x002d:
            boolean[] r4 = r0.h
            int r4 = com.google.android.exoplayer2.util.NalUnitUtil.findNalUnit(r3, r1, r2, r4)
            if (r4 != r2) goto L_0x0039
            r0.a(r3, r1, r2)
            return
        L_0x0039:
            int r8 = com.google.android.exoplayer2.util.NalUnitUtil.getNalUnitType(r3, r4)
            int r5 = r4 - r1
            if (r5 <= 0) goto L_0x0044
            r0.a(r3, r1, r4)
        L_0x0044:
            int r12 = r2 - r4
            long r6 = r0.g
            long r9 = (long) r12
            long r6 = r6 - r9
            if (r5 >= 0) goto L_0x004e
            int r5 = -r5
            goto L_0x004f
        L_0x004e:
            r5 = 0
        L_0x004f:
            long r9 = r0.m
            boolean r11 = r0.l
            e7 r15 = r0.e
            e7 r14 = r0.d
            if (r11 == 0) goto L_0x006c
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r11 = r0.k
            boolean r11 = r11.needsSpsPps()
            if (r11 == 0) goto L_0x0062
            goto L_0x006c
        L_0x0062:
            r16 = r2
            r17 = r3
            r18 = r4
            r19 = r8
            goto L_0x013f
        L_0x006c:
            r14.endNalUnit(r5)
            r15.endNalUnit(r5)
            boolean r11 = r0.l
            if (r11 != 0) goto L_0x0108
            boolean r11 = r14.isCompleted()
            if (r11 == 0) goto L_0x0062
            boolean r11 = r15.isCompleted()
            if (r11 == 0) goto L_0x0062
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            byte[] r1 = r14.d
            int r13 = r14.e
            byte[] r1 = java.util.Arrays.copyOf(r1, r13)
            r11.add(r1)
            byte[] r1 = r15.d
            int r13 = r15.e
            byte[] r1 = java.util.Arrays.copyOf(r1, r13)
            r11.add(r1)
            byte[] r1 = r14.d
            int r13 = r14.e
            r16 = r2
            r2 = 3
            com.google.android.exoplayer2.util.NalUnitUtil$SpsData r1 = com.google.android.exoplayer2.util.NalUnitUtil.parseSpsNalUnit(r1, r2, r13)
            byte[] r13 = r15.d
            r17 = r3
            int r3 = r15.e
            com.google.android.exoplayer2.util.NalUnitUtil$PpsData r2 = com.google.android.exoplayer2.util.NalUnitUtil.parsePpsNalUnit(r13, r2, r3)
            int r3 = r1.a
            int r13 = r1.b
            r18 = r4
            int r4 = r1.c
            java.lang.String r3 = com.google.android.exoplayer2.util.CodecSpecificDataUtil.buildAvcCodecString(r3, r13, r4)
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r0.j
            com.google.android.exoplayer2.Format$Builder r13 = new com.google.android.exoplayer2.Format$Builder
            r13.<init>()
            r19 = r8
            java.lang.String r8 = r0.i
            com.google.android.exoplayer2.Format$Builder r8 = r13.setId((java.lang.String) r8)
            java.lang.String r13 = "video/avc"
            com.google.android.exoplayer2.Format$Builder r8 = r8.setSampleMimeType(r13)
            com.google.android.exoplayer2.Format$Builder r3 = r8.setCodecs(r3)
            int r8 = r1.e
            com.google.android.exoplayer2.Format$Builder r3 = r3.setWidth(r8)
            int r8 = r1.f
            com.google.android.exoplayer2.Format$Builder r3 = r3.setHeight(r8)
            float r8 = r1.g
            com.google.android.exoplayer2.Format$Builder r3 = r3.setPixelWidthHeightRatio(r8)
            com.google.android.exoplayer2.Format$Builder r3 = r3.setInitializationData(r11)
            com.google.android.exoplayer2.Format r3 = r3.build()
            r4.format(r3)
            r3 = 1
            r0.l = r3
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r3 = r0.k
            r3.putSps(r1)
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r1 = r0.k
            r1.putPps(r2)
            r14.reset()
            r15.reset()
            goto L_0x013f
        L_0x0108:
            r16 = r2
            r17 = r3
            r18 = r4
            r19 = r8
            boolean r1 = r14.isCompleted()
            if (r1 == 0) goto L_0x0128
            byte[] r1 = r14.d
            int r2 = r14.e
            r3 = 3
            com.google.android.exoplayer2.util.NalUnitUtil$SpsData r1 = com.google.android.exoplayer2.util.NalUnitUtil.parseSpsNalUnit(r1, r3, r2)
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r2 = r0.k
            r2.putSps(r1)
            r14.reset()
            goto L_0x013f
        L_0x0128:
            r3 = 3
            boolean r1 = r15.isCompleted()
            if (r1 == 0) goto L_0x013f
            byte[] r1 = r15.d
            int r2 = r15.e
            com.google.android.exoplayer2.util.NalUnitUtil$PpsData r1 = com.google.android.exoplayer2.util.NalUnitUtil.parsePpsNalUnit(r1, r3, r2)
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r2 = r0.k
            r2.putPps(r1)
            r15.reset()
        L_0x013f:
            e7 r1 = r0.f
            boolean r2 = r1.endNalUnit(r5)
            if (r2 == 0) goto L_0x015f
            byte[] r2 = r1.d
            int r3 = r1.e
            int r2 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r2, r3)
            byte[] r3 = r1.d
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.o
            r4.reset(r3, r2)
            r2 = 4
            r4.setPosition(r2)
            com.google.android.exoplayer2.extractor.ts.SeiReader r2 = r0.a
            r2.consume(r9, r4)
        L_0x015f:
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r9 = r0.k
            boolean r13 = r0.l
            boolean r2 = r0.n
            r10 = r6
            r3 = r14
            r14 = r2
            boolean r2 = r9.endNalUnit(r10, r12, r13, r14)
            if (r2 == 0) goto L_0x0171
            r2 = 0
            r0.n = r2
        L_0x0171:
            long r9 = r0.m
            boolean r2 = r0.l
            if (r2 == 0) goto L_0x0183
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r2 = r0.k
            boolean r2 = r2.needsSpsPps()
            if (r2 == 0) goto L_0x0180
            goto L_0x0183
        L_0x0180:
            r2 = r19
            goto L_0x018b
        L_0x0183:
            r2 = r19
            r3.startNalUnit(r2)
            r15.startNalUnit(r2)
        L_0x018b:
            r1.startNalUnit(r2)
            com.google.android.exoplayer2.extractor.ts.H264Reader$a r5 = r0.k
            r8 = r2
            r5.startNalUnit(r6, r8, r9)
            int r1 = r18 + 3
            r2 = r16
            r3 = r17
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H264Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.i = trackIdGenerator.getFormatId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.j = track;
        this.k = new a(track, this.b, this.c);
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        boolean z;
        this.m = j2;
        boolean z2 = this.n;
        if ((i2 & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.n = z2 | z;
    }

    public void seek() {
        this.g = 0;
        this.n = false;
        NalUnitUtil.clearPrefixFlags(this.h);
        this.d.reset();
        this.e.reset();
        this.f.reset();
        a aVar = this.k;
        if (aVar != null) {
            aVar.reset();
        }
    }
}
