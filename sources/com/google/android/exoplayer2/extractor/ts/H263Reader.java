package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

public final class H263Reader implements ElementaryStreamReader {
    public static final float[] l = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 1.0f};
    @Nullable
    public final a a;
    @Nullable
    public final ParsableByteArray b;
    public final boolean[] c;
    public final a d;
    @Nullable
    public final e7 e;
    public b f;
    public long g;
    public String h;
    public TrackOutput i;
    public boolean j;
    public long k;

    public static final class a {
        public static final byte[] f = {0, 0, 1};
        public boolean a;
        public int b;
        public int c;
        public int d;
        public byte[] e;

        public a(int i) {
            this.e = new byte[i];
        }

        public void onData(byte[] bArr, int i, int i2) {
            if (this.a) {
                int i3 = i2 - i;
                byte[] bArr2 = this.e;
                int length = bArr2.length;
                int i4 = this.c;
                if (length < i4 + i3) {
                    this.e = Arrays.copyOf(bArr2, (i4 + i3) * 2);
                }
                System.arraycopy(bArr, i, this.e, this.c, i3);
                this.c += i3;
            }
        }

        public boolean onStartCode(int i, int i2) {
            int i3 = this.b;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 != 3) {
                            if (i3 != 4) {
                                throw new IllegalStateException();
                            } else if (i == 179 || i == 181) {
                                this.c -= i2;
                                this.a = false;
                                return true;
                            }
                        } else if ((i & 240) != 32) {
                            Log.w("H263Reader", "Unexpected start code value");
                            reset();
                        } else {
                            this.d = this.c;
                            this.b = 4;
                        }
                    } else if (i > 31) {
                        Log.w("H263Reader", "Unexpected start code value");
                        reset();
                    } else {
                        this.b = 3;
                    }
                } else if (i != 181) {
                    Log.w("H263Reader", "Unexpected start code value");
                    reset();
                } else {
                    this.b = 2;
                }
            } else if (i == 176) {
                this.b = 1;
                this.a = true;
            }
            onData(f, 0, 3);
            return false;
        }

        public void reset() {
            this.a = false;
            this.c = 0;
            this.b = 0;
        }
    }

    public static final class b {
        public final TrackOutput a;
        public boolean b;
        public boolean c;
        public boolean d;
        public int e;
        public int f;
        public long g;
        public long h;

        public b(TrackOutput trackOutput) {
            this.a = trackOutput;
        }

        public void onData(byte[] bArr, int i, int i2) {
            boolean z;
            if (this.c) {
                int i3 = this.f;
                int i4 = (i + 1) - i3;
                if (i4 < i2) {
                    if (((bArr[i4] & 192) >> 6) == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    this.d = z;
                    this.c = false;
                    return;
                }
                this.f = (i2 - i) + i3;
            }
        }

        public void onDataEnd(long j, int i, boolean z) {
            if (this.e == 182 && z && this.b) {
                boolean z2 = this.d;
                this.a.sampleMetadata(this.h, z2 ? 1 : 0, (int) (j - this.g), i, (TrackOutput.CryptoData) null);
            }
            if (this.e != 179) {
                this.g = j;
            }
        }

        public void onStartCode(int i, long j) {
            boolean z;
            this.e = i;
            this.d = false;
            boolean z2 = true;
            if (i == 182 || i == 179) {
                z = true;
            } else {
                z = false;
            }
            this.b = z;
            if (i != 182) {
                z2 = false;
            }
            this.c = z2;
            this.f = 0;
            this.h = j;
        }

        public void reset() {
            this.b = false;
            this.c = false;
            this.d = false;
            this.e = -1;
        }
    }

    public H263Reader() {
        this((a) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0110  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0189  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01cb A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r19) {
        /*
            r18 = this;
            r0 = r18
            com.google.android.exoplayer2.extractor.ts.H263Reader$b r1 = r0.f
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r0.i
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
            int r1 = r19.getPosition()
            int r2 = r19.limit()
            byte[] r3 = r19.getData()
            long r4 = r0.g
            int r6 = r19.bytesLeft()
            long r6 = (long) r6
            long r4 = r4 + r6
            r0.g = r4
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r0.i
            int r5 = r19.bytesLeft()
            r6 = r19
            r4.sampleData(r6, r5)
        L_0x002d:
            boolean[] r4 = r0.c
            int r4 = com.google.android.exoplayer2.util.NalUnitUtil.findNalUnit(r3, r1, r2, r4)
            com.google.android.exoplayer2.extractor.ts.H263Reader$a r5 = r0.d
            e7 r7 = r0.e
            if (r4 != r2) goto L_0x004b
            boolean r4 = r0.j
            if (r4 != 0) goto L_0x0040
            r5.onData(r3, r1, r2)
        L_0x0040:
            com.google.android.exoplayer2.extractor.ts.H263Reader$b r4 = r0.f
            r4.onData(r3, r1, r2)
            if (r7 == 0) goto L_0x004a
            r7.appendToNalUnit(r3, r1, r2)
        L_0x004a:
            return
        L_0x004b:
            byte[] r8 = r19.getData()
            int r9 = r4 + 3
            byte r8 = r8[r9]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r10 = r4 - r1
            boolean r11 = r0.j
            if (r11 != 0) goto L_0x0180
            if (r10 <= 0) goto L_0x0060
            r5.onData(r3, r1, r4)
        L_0x0060:
            if (r10 >= 0) goto L_0x0064
            int r11 = -r10
            goto L_0x0065
        L_0x0064:
            r11 = 0
        L_0x0065:
            boolean r11 = r5.onStartCode(r8, r11)
            if (r11 == 0) goto L_0x0180
            com.google.android.exoplayer2.extractor.TrackOutput r11 = r0.i
            int r14 = r5.d
            java.lang.String r15 = r0.h
            java.lang.Object r15 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r15)
            java.lang.String r15 = (java.lang.String) r15
            byte[] r12 = r5.e
            int r5 = r5.c
            byte[] r5 = java.util.Arrays.copyOf(r12, r5)
            com.google.android.exoplayer2.util.ParsableBitArray r12 = new com.google.android.exoplayer2.util.ParsableBitArray
            r12.<init>(r5)
            r12.skipBytes(r14)
            r14 = 4
            r12.skipBytes(r14)
            r12.skipBit()
            r13 = 8
            r12.skipBits(r13)
            boolean r16 = r12.readBit()
            r13 = 3
            if (r16 == 0) goto L_0x00a0
            r12.skipBits(r14)
            r12.skipBits(r13)
        L_0x00a0:
            int r14 = r12.readBits(r14)
            java.lang.String r13 = "Invalid aspect ratio"
            java.lang.String r6 = "H263Reader"
            r17 = r9
            r9 = 15
            if (r14 != r9) goto L_0x00c2
            r9 = 8
            int r14 = r12.readBits(r9)
            int r9 = r12.readBits(r9)
            if (r9 != 0) goto L_0x00be
            com.google.android.exoplayer2.util.Log.w(r6, r13)
            goto L_0x00cd
        L_0x00be:
            float r13 = (float) r14
            float r9 = (float) r9
            float r13 = r13 / r9
            goto L_0x00cf
        L_0x00c2:
            r9 = 7
            if (r14 >= r9) goto L_0x00ca
            float[] r9 = l
            r13 = r9[r14]
            goto L_0x00cf
        L_0x00ca:
            com.google.android.exoplayer2.util.Log.w(r6, r13)
        L_0x00cd:
            r13 = 1065353216(0x3f800000, float:1.0)
        L_0x00cf:
            boolean r9 = r12.readBit()
            r14 = 2
            if (r9 == 0) goto L_0x0109
            r12.skipBits(r14)
            r9 = 1
            r12.skipBits(r9)
            boolean r9 = r12.readBit()
            if (r9 == 0) goto L_0x0109
            r9 = 15
            r12.skipBits(r9)
            r12.skipBit()
            r12.skipBits(r9)
            r12.skipBit()
            r12.skipBits(r9)
            r12.skipBit()
            r14 = 3
            r12.skipBits(r14)
            r14 = 11
            r12.skipBits(r14)
            r12.skipBit()
            r12.skipBits(r9)
            r12.skipBit()
        L_0x0109:
            r9 = 2
            int r9 = r12.readBits(r9)
            if (r9 == 0) goto L_0x0115
            java.lang.String r9 = "Unhandled video object layer shape"
            com.google.android.exoplayer2.util.Log.w(r6, r9)
        L_0x0115:
            r12.skipBit()
            r9 = 16
            int r9 = r12.readBits(r9)
            r12.skipBit()
            boolean r14 = r12.readBit()
            if (r14 == 0) goto L_0x013c
            if (r9 != 0) goto L_0x012f
            java.lang.String r9 = "Invalid vop_increment_time_resolution"
            com.google.android.exoplayer2.util.Log.w(r6, r9)
            goto L_0x013c
        L_0x012f:
            int r9 = r9 + -1
            r6 = 0
        L_0x0132:
            if (r9 <= 0) goto L_0x0139
            int r6 = r6 + 1
            int r9 = r9 >> 1
            goto L_0x0132
        L_0x0139:
            r12.skipBits(r6)
        L_0x013c:
            r12.skipBit()
            r6 = 13
            int r9 = r12.readBits(r6)
            r12.skipBit()
            int r6 = r12.readBits(r6)
            r12.skipBit()
            r12.skipBit()
            com.google.android.exoplayer2.Format$Builder r12 = new com.google.android.exoplayer2.Format$Builder
            r12.<init>()
            com.google.android.exoplayer2.Format$Builder r12 = r12.setId((java.lang.String) r15)
            java.lang.String r14 = "video/mp4v-es"
            com.google.android.exoplayer2.Format$Builder r12 = r12.setSampleMimeType(r14)
            com.google.android.exoplayer2.Format$Builder r9 = r12.setWidth(r9)
            com.google.android.exoplayer2.Format$Builder r6 = r9.setHeight(r6)
            com.google.android.exoplayer2.Format$Builder r6 = r6.setPixelWidthHeightRatio(r13)
            java.util.List r5 = java.util.Collections.singletonList(r5)
            com.google.android.exoplayer2.Format$Builder r5 = r6.setInitializationData(r5)
            com.google.android.exoplayer2.Format r5 = r5.build()
            r11.format(r5)
            r5 = 1
            r0.j = r5
            goto L_0x0182
        L_0x0180:
            r17 = r9
        L_0x0182:
            com.google.android.exoplayer2.extractor.ts.H263Reader$b r5 = r0.f
            r5.onData(r3, r1, r4)
            if (r7 == 0) goto L_0x01cb
            if (r10 <= 0) goto L_0x0190
            r7.appendToNalUnit(r3, r1, r4)
            r12 = 0
            goto L_0x0191
        L_0x0190:
            int r12 = -r10
        L_0x0191:
            boolean r1 = r7.endNalUnit(r12)
            if (r1 == 0) goto L_0x01b9
            byte[] r1 = r7.d
            int r5 = r7.e
            int r1 = com.google.android.exoplayer2.util.NalUnitUtil.unescapeStream(r1, r5)
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r0.b
            java.lang.Object r6 = com.google.android.exoplayer2.util.Util.castNonNull(r5)
            com.google.android.exoplayer2.util.ParsableByteArray r6 = (com.google.android.exoplayer2.util.ParsableByteArray) r6
            byte[] r9 = r7.d
            r6.reset(r9, r1)
            com.google.android.exoplayer2.extractor.ts.a r1 = r0.a
            java.lang.Object r1 = com.google.android.exoplayer2.util.Util.castNonNull(r1)
            com.google.android.exoplayer2.extractor.ts.a r1 = (com.google.android.exoplayer2.extractor.ts.a) r1
            long r9 = r0.k
            r1.consume(r9, r5)
        L_0x01b9:
            r1 = 178(0xb2, float:2.5E-43)
            if (r8 != r1) goto L_0x01cb
            byte[] r1 = r19.getData()
            int r5 = r4 + 2
            byte r1 = r1[r5]
            r5 = 1
            if (r1 != r5) goto L_0x01cb
            r7.startNalUnit(r8)
        L_0x01cb:
            int r1 = r2 - r4
            long r4 = r0.g
            long r6 = (long) r1
            long r4 = r4 - r6
            com.google.android.exoplayer2.extractor.ts.H263Reader$b r6 = r0.f
            boolean r7 = r0.j
            r6.onDataEnd(r4, r1, r7)
            com.google.android.exoplayer2.extractor.ts.H263Reader$b r1 = r0.f
            long r4 = r0.k
            r1.onStartCode(r8, r4)
            r6 = r19
            r1 = r17
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.H263Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.h = trackIdGenerator.getFormatId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 2);
        this.i = track;
        this.f = new b(track);
        a aVar = this.a;
        if (aVar != null) {
            aVar.createTracks(extractorOutput, trackIdGenerator);
        }
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.k = j2;
    }

    public void seek() {
        NalUnitUtil.clearPrefixFlags(this.c);
        this.d.reset();
        b bVar = this.f;
        if (bVar != null) {
            bVar.reset();
        }
        e7 e7Var = this.e;
        if (e7Var != null) {
            e7Var.reset();
        }
        this.g = 0;
    }

    public H263Reader(@Nullable a aVar) {
        this.a = aVar;
        this.c = new boolean[4];
        this.d = new a(128);
        if (aVar != null) {
            this.e = new e7(178, 128);
            this.b = new ParsableByteArray();
            return;
        }
        this.e = null;
        this.b = null;
    }
}
