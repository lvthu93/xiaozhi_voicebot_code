package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class LatmReader implements ElementaryStreamReader {
    @Nullable
    public final String a;
    public final ParsableByteArray b;
    public final ParsableBitArray c;
    public TrackOutput d;
    public String e;
    public Format f;
    public int g;
    public int h;
    public int i;
    public int j;
    public long k;
    public boolean l;
    public int m;
    public int n;
    public int o;
    public boolean p;
    public long q;
    public int r;
    public long s;
    public int t;
    @Nullable
    public String u;

    public LatmReader(@Nullable String str) {
        this.a = str;
        ParsableByteArray parsableByteArray = new ParsableByteArray(1024);
        this.b = parsableByteArray;
        this.c = new ParsableBitArray(parsableByteArray.getData());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01a0, code lost:
        if (r0.l == false) goto L_0x01f7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r20) throws com.google.android.exoplayer2.ParserException {
        /*
            r19 = this;
            r0 = r19
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r0.d
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r1)
        L_0x0007:
            int r1 = r20.bytesLeft()
            if (r1 <= 0) goto L_0x025e
            int r1 = r0.g
            r2 = 1
            r3 = 86
            if (r1 == 0) goto L_0x0252
            r4 = 0
            r5 = 2
            if (r1 == r2) goto L_0x023a
            r3 = 3
            r6 = 8
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r0.b
            com.google.android.exoplayer2.util.ParsableBitArray r8 = r0.c
            if (r1 == r5) goto L_0x0213
            if (r1 != r3) goto L_0x020d
            int r1 = r20.bytesLeft()
            int r9 = r0.i
            int r10 = r0.h
            int r9 = r9 - r10
            int r1 = java.lang.Math.min(r1, r9)
            byte[] r9 = r8.a
            int r10 = r0.h
            r11 = r20
            r11.readBytes(r9, r10, r1)
            int r9 = r0.h
            int r9 = r9 + r1
            r0.h = r9
            int r1 = r0.i
            if (r9 != r1) goto L_0x0007
            r8.setPosition(r4)
            boolean r1 = r8.readBit()
            if (r1 != 0) goto L_0x019e
            r0.l = r2
            int r1 = r8.readBits(r2)
            if (r1 != r2) goto L_0x0058
            int r9 = r8.readBits(r2)
            goto L_0x0059
        L_0x0058:
            r9 = 0
        L_0x0059:
            r0.m = r9
            if (r9 != 0) goto L_0x0198
            if (r1 != r2) goto L_0x0069
            int r9 = r8.readBits(r5)
            int r9 = r9 + r2
            int r9 = r9 * 8
            r8.readBits(r9)
        L_0x0069:
            boolean r9 = r8.readBit()
            if (r9 == 0) goto L_0x0192
            r9 = 6
            int r10 = r8.readBits(r9)
            r0.n = r10
            r10 = 4
            int r12 = r8.readBits(r10)
            int r13 = r8.readBits(r3)
            if (r12 != 0) goto L_0x018c
            if (r13 != 0) goto L_0x018c
            if (r1 != 0) goto L_0x00fc
            int r12 = r8.getPosition()
            int r13 = r8.bitsLeft()
            com.google.android.exoplayer2.audio.AacUtil$Config r14 = com.google.android.exoplayer2.audio.AacUtil.parseAudioSpecificConfig(r8, r2)
            java.lang.String r15 = r14.c
            r0.u = r15
            int r15 = r14.a
            r0.r = r15
            int r14 = r14.b
            r0.t = r14
            int r14 = r8.bitsLeft()
            int r13 = r13 - r14
            r8.setPosition(r12)
            int r12 = r13 + 7
            int r12 = r12 / r6
            byte[] r12 = new byte[r12]
            r8.readBits(r12, r4, r13)
            com.google.android.exoplayer2.Format$Builder r13 = new com.google.android.exoplayer2.Format$Builder
            r13.<init>()
            java.lang.String r14 = r0.e
            com.google.android.exoplayer2.Format$Builder r13 = r13.setId((java.lang.String) r14)
            java.lang.String r14 = "audio/mp4a-latm"
            com.google.android.exoplayer2.Format$Builder r13 = r13.setSampleMimeType(r14)
            java.lang.String r14 = r0.u
            com.google.android.exoplayer2.Format$Builder r13 = r13.setCodecs(r14)
            int r14 = r0.t
            com.google.android.exoplayer2.Format$Builder r13 = r13.setChannelCount(r14)
            int r14 = r0.r
            com.google.android.exoplayer2.Format$Builder r13 = r13.setSampleRate(r14)
            java.util.List r12 = java.util.Collections.singletonList(r12)
            com.google.android.exoplayer2.Format$Builder r12 = r13.setInitializationData(r12)
            java.lang.String r13 = r0.a
            com.google.android.exoplayer2.Format$Builder r12 = r12.setLanguage(r13)
            com.google.android.exoplayer2.Format r12 = r12.build()
            com.google.android.exoplayer2.Format r13 = r0.f
            boolean r13 = r12.equals(r13)
            if (r13 != 0) goto L_0x0126
            r0.f = r12
            int r13 = r12.ad
            long r13 = (long) r13
            r15 = 1024000000(0x3d090000, double:5.059232213E-315)
            long r13 = r15 / r13
            r0.s = r13
            com.google.android.exoplayer2.extractor.TrackOutput r13 = r0.d
            r13.format(r12)
            goto L_0x0126
        L_0x00fc:
            int r12 = r8.readBits(r5)
            int r12 = r12 + r2
            int r12 = r12 * 8
            int r12 = r8.readBits(r12)
            long r12 = (long) r12
            int r13 = (int) r12
            int r12 = r8.bitsLeft()
            com.google.android.exoplayer2.audio.AacUtil$Config r14 = com.google.android.exoplayer2.audio.AacUtil.parseAudioSpecificConfig(r8, r2)
            java.lang.String r15 = r14.c
            r0.u = r15
            int r15 = r14.a
            r0.r = r15
            int r14 = r14.b
            r0.t = r14
            int r14 = r8.bitsLeft()
            int r12 = r12 - r14
            int r13 = r13 - r12
            r8.skipBits(r13)
        L_0x0126:
            int r12 = r8.readBits(r3)
            r0.o = r12
            if (r12 == 0) goto L_0x0151
            if (r12 == r2) goto L_0x014b
            if (r12 == r3) goto L_0x0147
            if (r12 == r10) goto L_0x0147
            r3 = 5
            if (r12 == r3) goto L_0x0147
            if (r12 == r9) goto L_0x0143
            r3 = 7
            if (r12 != r3) goto L_0x013d
            goto L_0x0143
        L_0x013d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x0143:
            r8.skipBits(r2)
            goto L_0x0154
        L_0x0147:
            r8.skipBits(r9)
            goto L_0x0154
        L_0x014b:
            r3 = 9
            r8.skipBits(r3)
            goto L_0x0154
        L_0x0151:
            r8.skipBits(r6)
        L_0x0154:
            boolean r3 = r8.readBit()
            r0.p = r3
            r9 = 0
            r0.q = r9
            if (r3 == 0) goto L_0x0182
            if (r1 != r2) goto L_0x0171
            int r1 = r8.readBits(r5)
            int r1 = r1 + r2
            int r1 = r1 * 8
            int r1 = r8.readBits(r1)
            long r1 = (long) r1
            r0.q = r1
            goto L_0x0182
        L_0x0171:
            boolean r1 = r8.readBit()
            long r2 = r0.q
            long r2 = r2 << r6
            int r5 = r8.readBits(r6)
            long r9 = (long) r5
            long r2 = r2 + r9
            r0.q = r2
            if (r1 != 0) goto L_0x0171
        L_0x0182:
            boolean r1 = r8.readBit()
            if (r1 == 0) goto L_0x01a3
            r8.skipBits(r6)
            goto L_0x01a3
        L_0x018c:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x0192:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x0198:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x019e:
            boolean r1 = r0.l
            if (r1 != 0) goto L_0x01a3
            goto L_0x01f7
        L_0x01a3:
            int r1 = r0.m
            if (r1 != 0) goto L_0x0207
            int r1 = r0.n
            if (r1 != 0) goto L_0x0201
            int r1 = r0.o
            if (r1 != 0) goto L_0x01fb
            r1 = 0
        L_0x01b0:
            int r2 = r8.readBits(r6)
            int r1 = r1 + r2
            r3 = 255(0xff, float:3.57E-43)
            if (r2 == r3) goto L_0x01b0
            int r2 = r8.getPosition()
            r3 = r2 & 7
            if (r3 != 0) goto L_0x01c7
            int r2 = r2 >> 3
            r7.setPosition(r2)
            goto L_0x01d3
        L_0x01c7:
            byte[] r2 = r7.getData()
            int r3 = r1 * 8
            r8.readBits(r2, r4, r3)
            r7.setPosition(r4)
        L_0x01d3:
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.d
            r2.sampleData(r7, r1)
            com.google.android.exoplayer2.extractor.TrackOutput r12 = r0.d
            long r13 = r0.k
            r15 = 1
            r17 = 0
            r18 = 0
            r16 = r1
            r12.sampleMetadata(r13, r15, r16, r17, r18)
            long r1 = r0.k
            long r5 = r0.s
            long r1 = r1 + r5
            r0.k = r1
            boolean r1 = r0.p
            if (r1 == 0) goto L_0x01f7
            long r1 = r0.q
            int r2 = (int) r1
            r8.skipBits(r2)
        L_0x01f7:
            r0.g = r4
            goto L_0x0007
        L_0x01fb:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x0201:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x0207:
            com.google.android.exoplayer2.ParserException r1 = new com.google.android.exoplayer2.ParserException
            r1.<init>()
            throw r1
        L_0x020d:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x0213:
            r11 = r20
            int r1 = r0.j
            r1 = r1 & -225(0xffffffffffffff1f, float:NaN)
            int r1 = r1 << r6
            int r2 = r20.readUnsignedByte()
            r1 = r1 | r2
            r0.i = r1
            byte[] r2 = r7.getData()
            int r2 = r2.length
            if (r1 <= r2) goto L_0x0234
            int r1 = r0.i
            r7.reset((int) r1)
            byte[] r1 = r7.getData()
            r8.reset((byte[]) r1)
        L_0x0234:
            r0.h = r4
            r0.g = r3
            goto L_0x0007
        L_0x023a:
            r11 = r20
            int r1 = r20.readUnsignedByte()
            r2 = r1 & 224(0xe0, float:3.14E-43)
            r6 = 224(0xe0, float:3.14E-43)
            if (r2 != r6) goto L_0x024c
            r0.j = r1
            r0.g = r5
            goto L_0x0007
        L_0x024c:
            if (r1 == r3) goto L_0x0007
            r0.g = r4
            goto L_0x0007
        L_0x0252:
            r11 = r20
            int r1 = r20.readUnsignedByte()
            if (r1 != r3) goto L_0x0007
            r0.g = r2
            goto L_0x0007
        L_0x025e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.LatmReader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.d = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
        this.e = trackIdGenerator.getFormatId();
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.k = j2;
    }

    public void seek() {
        this.g = 0;
        this.l = false;
    }
}
