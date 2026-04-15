package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TsExtractor implements Extractor {
    public final int a;
    public final int b;
    public final List<TimestampAdjuster> c;
    public final ParsableByteArray d;
    public final SparseIntArray e;
    public final TsPayloadReader.Factory f;
    public final SparseArray<TsPayloadReader> g;
    public final SparseBooleanArray h;
    public final SparseBooleanArray i;
    public final tc j;
    public sc k;
    public ExtractorOutput l;
    public int m;
    public boolean n;
    public boolean o;
    public boolean p;
    public TsPayloadReader q;
    public int r;
    public int s;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public class a implements SectionPayloadReader {
        public final ParsableBitArray a = new ParsableBitArray(new byte[4]);

        public a() {
        }

        public void consume(ParsableByteArray parsableByteArray) {
            TsExtractor tsExtractor;
            if (parsableByteArray.readUnsignedByte() == 0 && (parsableByteArray.readUnsignedByte() & 128) != 0) {
                parsableByteArray.skipBytes(6);
                int bytesLeft = parsableByteArray.bytesLeft() / 4;
                int i = 0;
                while (true) {
                    tsExtractor = TsExtractor.this;
                    if (i >= bytesLeft) {
                        break;
                    }
                    ParsableBitArray parsableBitArray = this.a;
                    parsableByteArray.readBytes(parsableBitArray, 4);
                    int readBits = parsableBitArray.readBits(16);
                    parsableBitArray.skipBits(3);
                    if (readBits == 0) {
                        parsableBitArray.skipBits(13);
                    } else {
                        int readBits2 = parsableBitArray.readBits(13);
                        if (tsExtractor.g.get(readBits2) == null) {
                            tsExtractor.g.put(readBits2, new SectionReader(new b(readBits2)));
                            tsExtractor.m++;
                        }
                    }
                    i++;
                }
                if (tsExtractor.a != 2) {
                    tsExtractor.g.remove(0);
                }
            }
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }
    }

    public class b implements SectionPayloadReader {
        public final ParsableBitArray a = new ParsableBitArray(new byte[5]);
        public final SparseArray<TsPayloadReader> b = new SparseArray<>();
        public final SparseIntArray c = new SparseIntArray();
        public final int d;

        public b(int i) {
            this.d = i;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0142, code lost:
            if (r27.readUnsignedByte() == 21) goto L_0x0144;
         */
        /* JADX WARNING: Removed duplicated region for block: B:116:0x0244 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:87:0x023e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void consume(com.google.android.exoplayer2.util.ParsableByteArray r27) {
            /*
                r26 = this;
                r0 = r26
                r1 = r27
                int r2 = r27.readUnsignedByte()
                r3 = 2
                if (r2 == r3) goto L_0x000c
                return
            L_0x000c:
                com.google.android.exoplayer2.extractor.ts.TsExtractor r2 = com.google.android.exoplayer2.extractor.ts.TsExtractor.this
                int r4 = r2.a
                r5 = 0
                r6 = 1
                if (r4 == r6) goto L_0x0032
                if (r4 == r3) goto L_0x0032
                int r4 = r2.m
                if (r4 != r6) goto L_0x001b
                goto L_0x0032
            L_0x001b:
                com.google.android.exoplayer2.util.TimestampAdjuster r4 = new com.google.android.exoplayer2.util.TimestampAdjuster
                java.util.List<com.google.android.exoplayer2.util.TimestampAdjuster> r7 = r2.c
                java.lang.Object r7 = r7.get(r5)
                com.google.android.exoplayer2.util.TimestampAdjuster r7 = (com.google.android.exoplayer2.util.TimestampAdjuster) r7
                long r7 = r7.getFirstSampleTimestampUs()
                r4.<init>(r7)
                java.util.List<com.google.android.exoplayer2.util.TimestampAdjuster> r7 = r2.c
                r7.add(r4)
                goto L_0x003a
            L_0x0032:
                java.util.List<com.google.android.exoplayer2.util.TimestampAdjuster> r4 = r2.c
                java.lang.Object r4 = r4.get(r5)
                com.google.android.exoplayer2.util.TimestampAdjuster r4 = (com.google.android.exoplayer2.util.TimestampAdjuster) r4
            L_0x003a:
                int r7 = r27.readUnsignedByte()
                r7 = r7 & 128(0x80, float:1.794E-43)
                if (r7 != 0) goto L_0x0043
                return
            L_0x0043:
                r1.skipBytes(r6)
                int r7 = r27.readUnsignedShort()
                r8 = 3
                r1.skipBytes(r8)
                com.google.android.exoplayer2.util.ParsableBitArray r9 = r0.a
                r1.readBytes((com.google.android.exoplayer2.util.ParsableBitArray) r9, (int) r3)
                r9.skipBits(r8)
                r10 = 13
                int r11 = r9.readBits(r10)
                r2.s = r11
                r1.readBytes((com.google.android.exoplayer2.util.ParsableBitArray) r9, (int) r3)
                r11 = 4
                r9.skipBits(r11)
                r12 = 12
                int r13 = r9.readBits(r12)
                r1.skipBytes(r13)
                int r13 = r2.a
                r14 = 8192(0x2000, float:1.14794E-41)
                r15 = 0
                r6 = 21
                if (r13 != r3) goto L_0x0096
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r13 = r2.q
                if (r13 != 0) goto L_0x0096
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo r13 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo
                byte[] r3 = com.google.android.exoplayer2.util.Util.f
                r13.<init>(r6, r15, r15, r3)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$Factory r3 = r2.f
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r3 = r3.createPayloadReader(r6, r13)
                r2.q = r3
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r3 = r2.q
                com.google.android.exoplayer2.extractor.ExtractorOutput r13 = r2.l
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$TrackIdGenerator r15 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$TrackIdGenerator
                r15.<init>(r7, r6, r14)
                r3.init(r4, r13, r15)
            L_0x0096:
                android.util.SparseArray<com.google.android.exoplayer2.extractor.ts.TsPayloadReader> r3 = r0.b
                r3.clear()
                android.util.SparseIntArray r13 = r0.c
                r13.clear()
                int r15 = r27.bytesLeft()
            L_0x00a4:
                if (r15 <= 0) goto L_0x0259
                r14 = 5
                r1.readBytes((com.google.android.exoplayer2.util.ParsableBitArray) r9, (int) r14)
                r5 = 8
                int r5 = r9.readBits(r5)
                r9.skipBits(r8)
                int r8 = r9.readBits(r10)
                r9.skipBits(r11)
                int r17 = r9.readBits(r12)
                int r10 = r27.getPosition()
                int r12 = r17 + r10
                r18 = -1
                r19 = -1
                r20 = 0
                r21 = 0
            L_0x00cc:
                int r11 = r27.getPosition()
                if (r11 >= r12) goto L_0x01e1
                int r11 = r27.readUnsignedByte()
                int r22 = r27.readUnsignedByte()
                int r23 = r27.getPosition()
                int r6 = r23 + r22
                if (r6 <= r12) goto L_0x00e4
                goto L_0x01e1
            L_0x00e4:
                if (r11 != r14) goto L_0x0116
                long r22 = r27.readUnsignedInt()
                r24 = 1094921523(0x41432d33, double:5.409631094E-315)
                int r11 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
                if (r11 != 0) goto L_0x00f2
                goto L_0x011a
            L_0x00f2:
                r24 = 1161904947(0x45414333, double:5.74057318E-315)
                int r11 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
                if (r11 != 0) goto L_0x00fa
                goto L_0x012b
            L_0x00fa:
                r24 = 1094921524(0x41432d34, double:5.4096311E-315)
                int r11 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
                if (r11 != 0) goto L_0x0102
                goto L_0x0144
            L_0x0102:
                r24 = 1212503619(0x48455643, double:5.990563836E-315)
                int r11 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
                if (r11 != 0) goto L_0x016f
                r11 = 36
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r19 = 36
                goto L_0x01cc
            L_0x0116:
                r14 = 106(0x6a, float:1.49E-43)
                if (r11 != r14) goto L_0x0127
            L_0x011a:
                r11 = 129(0x81, float:1.81E-43)
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r19 = 129(0x81, float:1.81E-43)
                goto L_0x01cc
            L_0x0127:
                r14 = 122(0x7a, float:1.71E-43)
                if (r11 != r14) goto L_0x0138
            L_0x012b:
                r11 = 135(0x87, float:1.89E-43)
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r19 = 135(0x87, float:1.89E-43)
                goto L_0x01cc
            L_0x0138:
                r14 = 127(0x7f, float:1.78E-43)
                if (r11 != r14) goto L_0x0151
                int r11 = r27.readUnsignedByte()
                r14 = 21
                if (r11 != r14) goto L_0x016f
            L_0x0144:
                r11 = 172(0xac, float:2.41E-43)
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r19 = 172(0xac, float:2.41E-43)
                goto L_0x01cc
            L_0x0151:
                r14 = 123(0x7b, float:1.72E-43)
                if (r11 != r14) goto L_0x0162
                r11 = 138(0x8a, float:1.93E-43)
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r19 = 138(0x8a, float:1.93E-43)
                goto L_0x01cc
            L_0x0162:
                r14 = 10
                if (r11 != r14) goto L_0x0177
                r14 = 3
                java.lang.String r11 = r1.readString(r14)
                java.lang.String r20 = r11.trim()
            L_0x016f:
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                goto L_0x01cc
            L_0x0177:
                r14 = 89
                if (r11 != r14) goto L_0x01bd
                java.util.ArrayList r11 = new java.util.ArrayList
                r11.<init>()
            L_0x0180:
                int r14 = r27.getPosition()
                if (r14 >= r6) goto L_0x01b1
                r14 = 3
                java.lang.String r16 = r1.readString(r14)
                java.lang.String r14 = r16.trim()
                r16 = r9
                int r9 = r27.readUnsignedByte()
                r18 = r4
                r0 = 4
                byte[] r4 = new byte[r0]
                r25 = r7
                r7 = 0
                r1.readBytes(r4, r7, r0)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$DvbSubtitleInfo r7 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$DvbSubtitleInfo
                r7.<init>(r14, r9, r4)
                r11.add(r7)
                r0 = r26
                r9 = r16
                r4 = r18
                r7 = r25
                goto L_0x0180
            L_0x01b1:
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r21 = r11
                r19 = 89
                goto L_0x01cc
            L_0x01bd:
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r4 = 111(0x6f, float:1.56E-43)
                if (r11 != r4) goto L_0x01cc
                r4 = 257(0x101, float:3.6E-43)
                r19 = 257(0x101, float:3.6E-43)
            L_0x01cc:
                int r4 = r27.getPosition()
                int r6 = r6 - r4
                r1.skipBytes(r6)
                r0 = r26
                r9 = r16
                r4 = r18
                r7 = r25
                r6 = 21
                r14 = 5
                goto L_0x00cc
            L_0x01e1:
                r18 = r4
                r25 = r7
                r16 = r9
                r0 = 4
                r1.setPosition(r12)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo r4 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$EsInfo
                byte[] r6 = r27.getData()
                byte[] r6 = java.util.Arrays.copyOfRange(r6, r10, r12)
                r7 = r19
                r9 = r20
                r10 = r21
                r4.<init>(r7, r9, r10, r6)
                r6 = 6
                if (r5 == r6) goto L_0x0204
                r6 = 5
                if (r5 != r6) goto L_0x0206
            L_0x0204:
                int r5 = r4.a
            L_0x0206:
                int r17 = r17 + 5
                int r15 = r15 - r17
                int r6 = r2.a
                r7 = 2
                if (r6 != r7) goto L_0x0211
                r6 = r5
                goto L_0x0212
            L_0x0211:
                r6 = r8
            L_0x0212:
                android.util.SparseBooleanArray r7 = r2.h
                boolean r7 = r7.get(r6)
                if (r7 == 0) goto L_0x021d
                r7 = 21
                goto L_0x0244
            L_0x021d:
                int r7 = r2.a
                r9 = 2
                if (r7 != r9) goto L_0x0229
                r7 = 21
                if (r5 != r7) goto L_0x022b
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r4 = r2.q
                goto L_0x0231
            L_0x0229:
                r7 = 21
            L_0x022b:
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$Factory r9 = r2.f
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r4 = r9.createPayloadReader(r5, r4)
            L_0x0231:
                int r5 = r2.a
                r9 = 2
                if (r5 != r9) goto L_0x023e
                r5 = 8192(0x2000, float:1.14794E-41)
                int r9 = r13.get(r6, r5)
                if (r8 >= r9) goto L_0x0244
            L_0x023e:
                r13.put(r6, r8)
                r3.put(r6, r4)
            L_0x0244:
                r0 = r26
                r9 = r16
                r4 = r18
                r7 = r25
                r5 = 0
                r6 = 21
                r8 = 3
                r10 = 13
                r11 = 4
                r12 = 12
                r14 = 8192(0x2000, float:1.14794E-41)
                goto L_0x00a4
            L_0x0259:
                r18 = r4
                r25 = r7
                int r0 = r13.size()
                r7 = 0
            L_0x0262:
                if (r7 >= r0) goto L_0x02ad
                int r1 = r13.keyAt(r7)
                int r4 = r13.valueAt(r7)
                android.util.SparseBooleanArray r5 = r2.h
                r6 = 1
                r5.put(r1, r6)
                android.util.SparseBooleanArray r5 = r2.i
                r5.put(r4, r6)
                java.lang.Object r5 = r3.valueAt(r7)
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r5 = (com.google.android.exoplayer2.extractor.ts.TsPayloadReader) r5
                if (r5 == 0) goto L_0x02a0
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader r6 = r2.q
                if (r5 == r6) goto L_0x0294
                com.google.android.exoplayer2.extractor.ExtractorOutput r6 = r2.l
                com.google.android.exoplayer2.extractor.ts.TsPayloadReader$TrackIdGenerator r8 = new com.google.android.exoplayer2.extractor.ts.TsPayloadReader$TrackIdGenerator
                r9 = r25
                r10 = 8192(0x2000, float:1.14794E-41)
                r8.<init>(r9, r1, r10)
                r1 = r18
                r5.init(r1, r6, r8)
                goto L_0x029a
            L_0x0294:
                r1 = r18
                r9 = r25
                r10 = 8192(0x2000, float:1.14794E-41)
            L_0x029a:
                android.util.SparseArray<com.google.android.exoplayer2.extractor.ts.TsPayloadReader> r6 = r2.g
                r6.put(r4, r5)
                goto L_0x02a6
            L_0x02a0:
                r1 = r18
                r9 = r25
                r10 = 8192(0x2000, float:1.14794E-41)
            L_0x02a6:
                int r7 = r7 + 1
                r18 = r1
                r25 = r9
                goto L_0x0262
            L_0x02ad:
                int r0 = r2.a
                r1 = 2
                if (r0 != r1) goto L_0x02c4
                boolean r0 = r2.n
                if (r0 != 0) goto L_0x02c1
                com.google.android.exoplayer2.extractor.ExtractorOutput r0 = r2.l
                r0.endTracks()
                r0 = 0
                r2.m = r0
                r0 = 1
                r2.n = r0
            L_0x02c1:
                r3 = r26
                goto L_0x02e5
            L_0x02c4:
                r0 = 0
                android.util.SparseArray<com.google.android.exoplayer2.extractor.ts.TsPayloadReader> r1 = r2.g
                r3 = r26
                int r4 = r3.d
                r1.remove(r4)
                int r1 = r2.a
                r4 = 1
                if (r1 != r4) goto L_0x02d5
                r5 = 0
                goto L_0x02d9
            L_0x02d5:
                int r0 = r2.m
                int r5 = r0 + -1
            L_0x02d9:
                r2.m = r5
                if (r5 != 0) goto L_0x02e5
                com.google.android.exoplayer2.extractor.ExtractorOutput r0 = r2.l
                r0.endTracks()
                r0 = 1
                r2.n = r0
            L_0x02e5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.TsExtractor.b.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        }
    }

    public TsExtractor() {
        this(0);
    }

    public void init(ExtractorOutput extractorOutput) {
        this.l = extractorOutput;
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        boolean z;
        boolean z2;
        boolean z3;
        int i2;
        int i3;
        boolean z4;
        boolean z5;
        TsPayloadReader tsPayloadReader;
        boolean z6;
        int i4;
        boolean z7;
        ExtractorInput extractorInput2 = extractorInput;
        PositionHolder positionHolder2 = positionHolder;
        long length = extractorInput.getLength();
        boolean z8 = this.n;
        int i5 = this.a;
        if (z8) {
            if (length == -1 || i5 == 2) {
                z7 = false;
            } else {
                z7 = true;
            }
            tc tcVar = this.j;
            if (z7 && !tcVar.isDurationReadFinished()) {
                return tcVar.readDuration(extractorInput2, positionHolder2, this.s);
            }
            if (!this.o) {
                this.o = true;
                if (tcVar.getDurationUs() != -9223372036854775807L) {
                    sc scVar = r3;
                    z = false;
                    sc scVar2 = new sc(tcVar.getPcrTimestampAdjuster(), tcVar.getDurationUs(), length, this.s, this.b);
                    this.k = scVar;
                    this.l.seekMap(scVar.getSeekMap());
                } else {
                    z = false;
                    this.l.seekMap(new SeekMap.Unseekable(tcVar.getDurationUs()));
                }
            } else {
                z = false;
            }
            if (this.p) {
                this.p = z;
                seek(0, 0);
                if (extractorInput.getPosition() != 0) {
                    positionHolder2.a = 0;
                    return 1;
                }
            }
            z2 = true;
            sc scVar3 = this.k;
            if (scVar3 != null && scVar3.isSeeking()) {
                return this.k.handlePendingSeek(extractorInput2, positionHolder2);
            }
        } else {
            z2 = true;
            z = false;
        }
        ParsableByteArray parsableByteArray = this.d;
        byte[] data = parsableByteArray.getData();
        if (9400 - parsableByteArray.getPosition() < 188) {
            int bytesLeft = parsableByteArray.bytesLeft();
            if (bytesLeft > 0) {
                System.arraycopy(data, parsableByteArray.getPosition(), data, z, bytesLeft);
            }
            parsableByteArray.reset(data, bytesLeft);
        }
        while (true) {
            if (parsableByteArray.bytesLeft() >= 188) {
                z3 = true;
                break;
            }
            int limit = parsableByteArray.limit();
            int read = extractorInput2.read(data, limit, 9400 - limit);
            if (read == -1) {
                z3 = false;
                break;
            }
            parsableByteArray.setLimit(limit + read);
        }
        if (!z3) {
            return -1;
        }
        int position = parsableByteArray.getPosition();
        int limit2 = parsableByteArray.limit();
        int findSyncBytePosition = TsUtil.findSyncBytePosition(parsableByteArray.getData(), position, limit2);
        parsableByteArray.setPosition(findSyncBytePosition);
        int i6 = findSyncBytePosition + 188;
        if (i6 > limit2) {
            int i7 = (findSyncBytePosition - position) + this.r;
            this.r = i7;
            i2 = 2;
            if (i5 == 2 && i7 > 376) {
                throw new ParserException("Cannot find sync byte. Most likely not a Transport Stream.");
            }
        } else {
            i2 = 2;
            this.r = z;
        }
        int limit3 = parsableByteArray.limit();
        if (i6 > limit3) {
            return z;
        }
        int readInt = parsableByteArray.readInt();
        if ((8388608 & readInt) != 0) {
            parsableByteArray.setPosition(i6);
            return z;
        }
        if ((4194304 & readInt) != 0) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        int i8 = i3 | 0;
        int i9 = (2096896 & readInt) >> 8;
        if ((readInt & 32) != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        if ((readInt & 16) != 0) {
            z5 = true;
        } else {
            z5 = false;
        }
        if (z5) {
            tsPayloadReader = this.g.get(i9);
        } else {
            tsPayloadReader = null;
        }
        if (tsPayloadReader == null) {
            parsableByteArray.setPosition(i6);
            return z;
        }
        if (i5 != i2) {
            int i10 = readInt & 15;
            SparseIntArray sparseIntArray = this.e;
            int i11 = sparseIntArray.get(i9, i10 - 1);
            sparseIntArray.put(i9, i10);
            if (i11 == i10) {
                parsableByteArray.setPosition(i6);
                return z;
            } else if (i10 != ((i11 + z2) & 15)) {
                tsPayloadReader.seek();
            }
        }
        if (z4) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            if ((parsableByteArray.readUnsignedByte() & 64) != 0) {
                i4 = 2;
            } else {
                i4 = 0;
            }
            i8 |= i4;
            parsableByteArray.skipBytes(readUnsignedByte - (z2 ? 1 : 0));
        }
        boolean z9 = this.n;
        if (i5 == 2 || z9 || !this.i.get(i9, z)) {
            z6 = true;
        } else {
            z6 = false;
        }
        if (z6) {
            parsableByteArray.setLimit(i6);
            tsPayloadReader.consume(parsableByteArray, i8);
            parsableByteArray.setLimit(limit3);
        }
        if (i5 != 2 && !z9 && this.n && length != -1) {
            this.p = z2;
        }
        parsableByteArray.setPosition(i6);
        return z ? 1 : 0;
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        boolean z;
        sc scVar;
        boolean z2;
        if (this.a != 2) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        List<TimestampAdjuster> list = this.c;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            TimestampAdjuster timestampAdjuster = list.get(i2);
            if (timestampAdjuster.getTimestampOffsetUs() == -9223372036854775807L) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2 || !(timestampAdjuster.getTimestampOffsetUs() == 0 || timestampAdjuster.getFirstSampleTimestampUs() == j3)) {
                timestampAdjuster.reset(j3);
            }
        }
        if (!(j3 == 0 || (scVar = this.k) == null)) {
            scVar.setSeekTargetUs(j3);
        }
        this.d.reset(0);
        this.e.clear();
        int i3 = 0;
        while (true) {
            SparseArray<TsPayloadReader> sparseArray = this.g;
            if (i3 < sparseArray.size()) {
                sparseArray.valueAt(i3).seek();
                i3++;
            } else {
                this.r = 0;
                return;
            }
        }
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        boolean z;
        byte[] data = this.d.getData();
        extractorInput.peekFully(data, 0, 940);
        for (int i2 = 0; i2 < 188; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= 5) {
                    z = true;
                    break;
                } else if (data[(i3 * 188) + i2] != 71) {
                    z = false;
                    break;
                } else {
                    i3++;
                }
            }
            if (z) {
                extractorInput.skipFully(i2);
                return true;
            }
        }
        return false;
    }

    public TsExtractor(int i2) {
        this(1, i2, 112800);
    }

    public TsExtractor(int i2, int i3, int i4) {
        this(i2, new TimestampAdjuster(0), new DefaultTsPayloadReaderFactory(i3), i4);
    }

    public TsExtractor(int i2, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory) {
        this(i2, timestampAdjuster, factory, 112800);
    }

    public TsExtractor(int i2, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory factory, int i3) {
        TsPayloadReader.Factory factory2 = (TsPayloadReader.Factory) Assertions.checkNotNull(factory);
        this.f = factory2;
        this.b = i3;
        this.a = i2;
        if (i2 == 1 || i2 == 2) {
            this.c = Collections.singletonList(timestampAdjuster);
        } else {
            ArrayList arrayList = new ArrayList();
            this.c = arrayList;
            arrayList.add(timestampAdjuster);
        }
        this.d = new ParsableByteArray(new byte[9400], 0);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.h = sparseBooleanArray;
        this.i = new SparseBooleanArray();
        SparseArray<TsPayloadReader> sparseArray = new SparseArray<>();
        this.g = sparseArray;
        this.e = new SparseIntArray();
        this.j = new tc(i3);
        this.s = -1;
        sparseBooleanArray.clear();
        sparseArray.clear();
        SparseArray<TsPayloadReader> createInitialPayloadReaders = factory2.createInitialPayloadReaders();
        int size = createInitialPayloadReaders.size();
        for (int i4 = 0; i4 < size; i4++) {
            sparseArray.put(createInitialPayloadReaders.keyAt(i4), createInitialPayloadReaders.valueAt(i4));
        }
        sparseArray.put(0, new SectionReader(new a()));
        this.q = null;
    }
}
