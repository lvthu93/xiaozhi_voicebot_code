package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

public final class H265Reader implements ElementaryStreamReader {
    public final SeiReader a;
    public String b;
    public TrackOutput c;
    public a d;
    public boolean e;
    public final boolean[] f = new boolean[3];
    public final e7 g = new e7(32, 128);
    public final e7 h = new e7(33, 128);
    public final e7 i = new e7(34, 128);
    public final e7 j = new e7(39, 128);
    public final e7 k = new e7(40, 128);
    public long l;
    public long m;
    public final ParsableByteArray n = new ParsableByteArray();

    public static final class a {
        public final TrackOutput a;
        public long b;
        public boolean c;
        public int d;
        public long e;
        public boolean f;
        public boolean g;
        public boolean h;
        public boolean i;
        public boolean j;
        public long k;
        public long l;
        public boolean m;

        public a(TrackOutput trackOutput) {
            this.a = trackOutput;
        }

        public void endNalUnit(long j2, int i2, boolean z) {
            if (this.j && this.g) {
                this.m = this.c;
                this.j = false;
            } else if (this.h || this.g) {
                if (z && this.i) {
                    long j3 = this.b;
                    boolean z2 = this.m;
                    int i3 = (int) (j3 - this.k);
                    this.a.sampleMetadata(this.l, z2 ? 1 : 0, i3, i2 + ((int) (j2 - j3)), (TrackOutput.CryptoData) null);
                }
                this.k = this.b;
                this.l = this.e;
                this.m = this.c;
                this.i = true;
            }
        }

        public void readNalUnitData(byte[] bArr, int i2, int i3) {
            boolean z;
            if (this.f) {
                int i4 = this.d;
                int i5 = (i2 + 2) - i4;
                if (i5 < i3) {
                    if ((bArr[i5] & 128) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    this.g = z;
                    this.f = false;
                    return;
                }
                this.d = (i3 - i2) + i4;
            }
        }

        public void reset() {
            this.f = false;
            this.g = false;
            this.h = false;
            this.i = false;
            this.j = false;
        }

        public void startNalUnit(long j2, int i2, int i3, long j3, boolean z) {
            boolean z2;
            boolean z3;
            boolean z4;
            long j4 = j2;
            int i4 = i3;
            boolean z5 = false;
            this.g = false;
            this.h = false;
            this.e = j3;
            this.d = 0;
            this.b = j4;
            if (i4 < 32 || i4 == 40) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                if (this.i && !this.j) {
                    if (z) {
                        boolean z6 = this.m;
                        this.a.sampleMetadata(this.l, z6 ? 1 : 0, (int) (j4 - this.k), i2, (TrackOutput.CryptoData) null);
                    }
                    this.i = false;
                }
                if ((32 > i4 || i4 > 35) && i4 != 39) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                if (z4) {
                    this.h = !this.j;
                    this.j = true;
                }
            }
            if (i4 < 16 || i4 > 21) {
                z3 = false;
            } else {
                z3 = true;
            }
            this.c = z3;
            if (z3 || i4 <= 9) {
                z5 = true;
            }
            this.f = z5;
        }
    }

    public H265Reader(SeiReader seiReader) {
        this.a = seiReader;
    }

    @RequiresNonNull({"sampleReader"})
    public final void a(byte[] bArr, int i2, int i3) {
        this.d.readNalUnitData(bArr, i2, i3);
        if (!this.e) {
            this.g.appendToNalUnit(bArr, i2, i3);
            this.h.appendToNalUnit(bArr, i2, i3);
            this.i.appendToNalUnit(bArr, i2, i3);
        }
        this.j.appendToNalUnit(bArr, i2, i3);
        this.k.appendToNalUnit(bArr, i2, i3);
    }

    /* JADX WARNING: Removed duplicated region for block: B:142:0x0329  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0344  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x036e  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x037c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r30) {
        /*
            r29 = this;
            r0 = r29
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r0.c
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
            com.google.android.exoplayer2.extractor.ts.H265Reader$a r1 = r0.d
            com.google.android.exoplayer2.util.Util.castNonNull(r1)
        L_0x000c:
            int r1 = r30.bytesLeft()
            if (r1 <= 0) goto L_0x038e
            int r1 = r30.getPosition()
            int r2 = r30.limit()
            byte[] r3 = r30.getData()
            long r4 = r0.l
            int r6 = r30.bytesLeft()
            long r6 = (long) r6
            long r4 = r4 + r6
            r0.l = r4
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r0.c
            int r5 = r30.bytesLeft()
            r6 = r30
            r4.sampleData(r6, r5)
        L_0x0033:
            if (r1 >= r2) goto L_0x000c
            boolean[] r4 = r0.f
            int r4 = com.google.android.exoplayer2.util.NalUnitUtil.findNalUnit(r3, r1, r2, r4)
            if (r4 != r2) goto L_0x0041
            r0.a(r3, r1, r2)
            return
        L_0x0041:
            int r5 = com.google.android.exoplayer2.util.NalUnitUtil.getH265NalUnitType(r3, r4)
            int r7 = r4 - r1
            if (r7 <= 0) goto L_0x004c
            r0.a(r3, r1, r4)
        L_0x004c:
            int r10 = r2 - r4
            long r8 = r0.l
            long r11 = (long) r10
            long r8 = r8 - r11
            if (r7 >= 0) goto L_0x0056
            int r1 = -r7
            goto L_0x0057
        L_0x0056:
            r1 = 0
        L_0x0057:
            long r11 = r0.m
            com.google.android.exoplayer2.extractor.ts.H265Reader$a r7 = r0.d
            boolean r13 = r0.e
            r7.endNalUnit(r8, r10, r13)
            boolean r7 = r0.e
            e7 r15 = r0.i
            e7 r14 = r0.h
            e7 r13 = r0.g
            if (r7 != 0) goto L_0x030c
            r13.endNalUnit(r1)
            r14.endNalUnit(r1)
            r15.endNalUnit(r1)
            boolean r7 = r13.isCompleted()
            if (r7 == 0) goto L_0x030c
            boolean r7 = r14.isCompleted()
            if (r7 == 0) goto L_0x030c
            boolean r7 = r15.isCompleted()
            if (r7 == 0) goto L_0x030c
            com.google.android.exoplayer2.extractor.TrackOutput r7 = r0.c
            r16 = r2
            java.lang.String r2 = r0.b
            r17 = r3
            int r3 = r13.e
            int r6 = r14.e
            int r6 = r6 + r3
            r18 = r4
            int r4 = r15.e
            int r6 = r6 + r4
            byte[] r4 = new byte[r6]
            byte[] r6 = r13.d
            r19 = r5
            r5 = 0
            java.lang.System.arraycopy(r6, r5, r4, r5, r3)
            byte[] r3 = r14.d
            int r6 = r13.e
            r20 = r10
            int r10 = r14.e
            java.lang.System.arraycopy(r3, r5, r4, r6, r10)
            byte[] r3 = r15.d
            int r6 = r13.e
            int r10 = r14.e
            int r6 = r6 + r10
            int r10 = r15.e
            java.lang.System.arraycopy(r3, r5, r4, r6, r10)
            com.google.android.exoplayer2.util.ParsableNalUnitBitArray r3 = new com.google.android.exoplayer2.util.ParsableNalUnitBitArray
            byte[] r6 = r14.d
            int r10 = r14.e
            r3.<init>(r6, r5, r10)
            r5 = 44
            r3.skipBits(r5)
            r5 = 3
            int r5 = r3.readBits(r5)
            r3.skipBit()
            r6 = 88
            r3.skipBits(r6)
            r6 = 8
            r3.skipBits(r6)
            r6 = 0
            r10 = 0
        L_0x00da:
            if (r10 >= r5) goto L_0x00ef
            boolean r21 = r3.readBit()
            if (r21 == 0) goto L_0x00e4
            int r6 = r6 + 89
        L_0x00e4:
            boolean r21 = r3.readBit()
            if (r21 == 0) goto L_0x00ec
            int r6 = r6 + 8
        L_0x00ec:
            int r10 = r10 + 1
            goto L_0x00da
        L_0x00ef:
            r3.skipBits(r6)
            if (r5 <= 0) goto L_0x00fb
            int r6 = 8 - r5
            int r6 = r6 * 2
            r3.skipBits(r6)
        L_0x00fb:
            r3.readUnsignedExpGolombCodedInt()
            int r6 = r3.readUnsignedExpGolombCodedInt()
            r10 = 3
            if (r6 != r10) goto L_0x0108
            r3.skipBit()
        L_0x0108:
            int r10 = r3.readUnsignedExpGolombCodedInt()
            int r21 = r3.readUnsignedExpGolombCodedInt()
            boolean r22 = r3.readBit()
            r23 = r13
            r13 = 1
            if (r22 == 0) goto L_0x0147
            int r22 = r3.readUnsignedExpGolombCodedInt()
            int r24 = r3.readUnsignedExpGolombCodedInt()
            int r25 = r3.readUnsignedExpGolombCodedInt()
            int r26 = r3.readUnsignedExpGolombCodedInt()
            if (r6 == r13) goto L_0x0133
            r13 = 2
            if (r6 != r13) goto L_0x012f
            goto L_0x0133
        L_0x012f:
            r13 = 1
            r27 = 1
            goto L_0x0136
        L_0x0133:
            r13 = 1
            r27 = 2
        L_0x0136:
            if (r6 != r13) goto L_0x013a
            r6 = 2
            goto L_0x013b
        L_0x013a:
            r6 = 1
        L_0x013b:
            int r22 = r22 + r24
            int r22 = r22 * r27
            int r10 = r10 - r22
            int r25 = r25 + r26
            int r25 = r25 * r6
            int r21 = r21 - r25
        L_0x0147:
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            int r6 = r3.readUnsignedExpGolombCodedInt()
            boolean r13 = r3.readBit()
            if (r13 == 0) goto L_0x0159
            r13 = 0
            goto L_0x015a
        L_0x0159:
            r13 = r5
        L_0x015a:
            if (r13 > r5) goto L_0x0168
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            int r13 = r13 + 1
            goto L_0x015a
        L_0x0168:
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            boolean r5 = r3.readBit()
            r13 = 4
            if (r5 == 0) goto L_0x01cb
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x01cb
            r5 = 0
        L_0x0188:
            if (r5 >= r13) goto L_0x01cb
            r13 = 0
            r22 = r15
        L_0x018d:
            r15 = 6
            if (r13 >= r15) goto L_0x01c3
            boolean r15 = r3.readBit()
            if (r15 != 0) goto L_0x019c
            r3.readUnsignedExpGolombCodedInt()
            r24 = r8
            goto L_0x01b9
        L_0x019c:
            int r15 = r5 << 1
            int r15 = r15 + 4
            r24 = r8
            r8 = 1
            int r9 = r8 << r15
            r15 = 64
            int r9 = java.lang.Math.min(r15, r9)
            if (r5 <= r8) goto L_0x01b0
            r3.readSignedExpGolombCodedInt()
        L_0x01b0:
            r8 = 0
        L_0x01b1:
            if (r8 >= r9) goto L_0x01b9
            r3.readSignedExpGolombCodedInt()
            int r8 = r8 + 1
            goto L_0x01b1
        L_0x01b9:
            r8 = 3
            if (r5 != r8) goto L_0x01be
            r8 = 3
            goto L_0x01bf
        L_0x01be:
            r8 = 1
        L_0x01bf:
            int r13 = r13 + r8
            r8 = r24
            goto L_0x018d
        L_0x01c3:
            r24 = r8
            int r5 = r5 + 1
            r13 = 4
            r15 = r22
            goto L_0x0188
        L_0x01cb:
            r24 = r8
            r22 = r15
            r5 = 2
            r3.skipBits(r5)
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x01e7
            r5 = 8
            r3.skipBits(r5)
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
            r3.skipBit()
        L_0x01e7:
            int r5 = r3.readUnsignedExpGolombCodedInt()
            r8 = 0
            r9 = 0
            r13 = 0
        L_0x01ee:
            if (r8 >= r5) goto L_0x023d
            if (r8 == 0) goto L_0x01f6
            boolean r9 = r3.readBit()
        L_0x01f6:
            if (r9 == 0) goto L_0x0210
            r3.skipBit()
            r3.readUnsignedExpGolombCodedInt()
            r15 = 0
        L_0x01ff:
            if (r15 > r13) goto L_0x020d
            boolean r26 = r3.readBit()
            if (r26 == 0) goto L_0x020a
            r3.skipBit()
        L_0x020a:
            int r15 = r15 + 1
            goto L_0x01ff
        L_0x020d:
            r28 = r5
            goto L_0x0238
        L_0x0210:
            int r13 = r3.readUnsignedExpGolombCodedInt()
            int r15 = r3.readUnsignedExpGolombCodedInt()
            int r26 = r13 + r15
            r27 = 0
            r28 = r5
            r5 = 0
        L_0x021f:
            if (r5 >= r13) goto L_0x022a
            r3.readUnsignedExpGolombCodedInt()
            r3.skipBit()
            int r5 = r5 + 1
            goto L_0x021f
        L_0x022a:
            r5 = 0
        L_0x022b:
            if (r5 >= r15) goto L_0x0236
            r3.readUnsignedExpGolombCodedInt()
            r3.skipBit()
            int r5 = r5 + 1
            goto L_0x022b
        L_0x0236:
            r13 = r26
        L_0x0238:
            int r8 = r8 + 1
            r5 = r28
            goto L_0x01ee
        L_0x023d:
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x0254
            r5 = 0
        L_0x0244:
            int r8 = r3.readUnsignedExpGolombCodedInt()
            if (r5 >= r8) goto L_0x0254
            int r8 = r6 + 4
            int r8 = r8 + 1
            r3.skipBits(r8)
            int r5 = r5 + 1
            goto L_0x0244
        L_0x0254:
            r5 = 2
            r3.skipBits(r5)
            boolean r5 = r3.readBit()
            r6 = 24
            r8 = 1065353216(0x3f800000, float:1.0)
            if (r5 == 0) goto L_0x02c9
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x0296
            r5 = 8
            int r5 = r3.readBits(r5)
            r9 = 255(0xff, float:3.57E-43)
            if (r5 != r9) goto L_0x0284
            r5 = 16
            int r9 = r3.readBits(r5)
            int r5 = r3.readBits(r5)
            if (r9 == 0) goto L_0x0296
            if (r5 == 0) goto L_0x0296
            float r8 = (float) r9
            float r5 = (float) r5
            float r8 = r8 / r5
            goto L_0x0296
        L_0x0284:
            float[] r9 = com.google.android.exoplayer2.util.NalUnitUtil.b
            r13 = 17
            if (r5 >= r13) goto L_0x028d
            r8 = r9[r5]
            goto L_0x0296
        L_0x028d:
            r9 = 46
            java.lang.String r13 = "Unexpected aspect_ratio_idc value: "
            java.lang.String r15 = "H265Reader"
            defpackage.y2.t(r9, r13, r5, r15)
        L_0x0296:
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x029f
            r3.skipBit()
        L_0x029f:
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x02b2
            r5 = 4
            r3.skipBits(r5)
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x02b2
            r3.skipBits(r6)
        L_0x02b2:
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x02be
            r3.readUnsignedExpGolombCodedInt()
            r3.readUnsignedExpGolombCodedInt()
        L_0x02be:
            r3.skipBit()
            boolean r5 = r3.readBit()
            if (r5 == 0) goto L_0x02c9
            int r21 = r21 * 2
        L_0x02c9:
            r5 = r21
            byte[] r9 = r14.d
            int r13 = r14.e
            r15 = 0
            r3.reset(r9, r15, r13)
            r3.skipBits(r6)
            java.lang.String r3 = com.google.android.exoplayer2.util.CodecSpecificDataUtil.buildHevcCodecStringFromSps(r3)
            com.google.android.exoplayer2.Format$Builder r6 = new com.google.android.exoplayer2.Format$Builder
            r6.<init>()
            com.google.android.exoplayer2.Format$Builder r2 = r6.setId((java.lang.String) r2)
            java.lang.String r6 = "video/hevc"
            com.google.android.exoplayer2.Format$Builder r2 = r2.setSampleMimeType(r6)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setCodecs(r3)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setWidth(r10)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setHeight(r5)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setPixelWidthHeightRatio(r8)
            java.util.List r3 = java.util.Collections.singletonList(r4)
            com.google.android.exoplayer2.Format$Builder r2 = r2.setInitializationData(r3)
            com.google.android.exoplayer2.Format r2 = r2.build()
            r7.format(r2)
            r2 = 1
            r0.e = r2
            goto L_0x031c
        L_0x030c:
            r16 = r2
            r17 = r3
            r18 = r4
            r19 = r5
            r24 = r8
            r20 = r10
            r23 = r13
            r22 = r15
        L_0x031c:
            e7 r2 = r0.j
            boolean r3 = r2.endNalUnit(r1)
            com.google.android.exoplayer2.extractor.ts.SeiReader r4 = r0.a
            r5 = 5
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r0.n
            if (r3 == 0) goto L_0x033c
            byte[] r3 = r2.d
            int r7 = r2.e
            int r3 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r3, r7)
            byte[] r7 = r2.d
            r6.reset(r7, r3)
            r6.skipBytes(r5)
            r4.consume(r11, r6)
        L_0x033c:
            e7 r3 = r0.k
            boolean r1 = r3.endNalUnit(r1)
            if (r1 == 0) goto L_0x0357
            byte[] r1 = r3.d
            int r7 = r3.e
            int r1 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r1, r7)
            byte[] r7 = r3.d
            r6.reset(r7, r1)
            r6.skipBytes(r5)
            r4.consume(r11, r6)
        L_0x0357:
            long r12 = r0.m
            com.google.android.exoplayer2.extractor.ts.H265Reader$a r7 = r0.d
            boolean r1 = r0.e
            r8 = r24
            r10 = r20
            r11 = r19
            r4 = r23
            r5 = r14
            r14 = r1
            r7.startNalUnit(r8, r10, r11, r12, r14)
            boolean r1 = r0.e
            if (r1 != 0) goto L_0x037c
            r1 = r19
            r4.startNalUnit(r1)
            r5.startNalUnit(r1)
            r4 = r22
            r4.startNalUnit(r1)
            goto L_0x037e
        L_0x037c:
            r1 = r19
        L_0x037e:
            r2.startNalUnit(r1)
            r3.startNalUnit(r1)
            int r1 = r18 + 3
            r6 = r30
            r2 = r16
            r3 = r17
            goto L_0x0033
        L_0x038e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H265Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.b = trackIdGenerator.getFormatId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.c = track;
        this.d = new a(track);
        this.a.createTracks(extractorOutput, trackIdGenerator);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.m = j2;
    }

    public void seek() {
        this.l = 0;
        NalUnitUtil.clearPrefixFlags(this.f);
        this.g.reset();
        this.h.reset();
        this.i.reset();
        this.j.reset();
        this.k.reset();
        a aVar = this.d;
        if (aVar != null) {
            aVar.reset();
        }
    }
}
