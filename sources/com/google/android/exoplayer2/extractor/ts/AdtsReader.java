package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;

public final class AdtsReader implements ElementaryStreamReader {
    public static final byte[] v = {73, 68, 51};
    public final boolean a;
    public final ParsableBitArray b;
    public final ParsableByteArray c;
    @Nullable
    public final String d;
    public String e;
    public TrackOutput f;
    public TrackOutput g;
    public int h;
    public int i;
    public int j;
    public boolean k;
    public boolean l;
    public int m;
    public int n;
    public int o;
    public boolean p;
    public long q;
    public int r;
    public long s;
    public TrackOutput t;
    public long u;

    public AdtsReader(boolean z) {
        this(z, (String) null);
    }

    public static boolean isAdtsSyncWord(int i2) {
        return (i2 & 65526) == 65520;
    }

    /* JADX WARNING: Removed duplicated region for block: B:153:0x02a2 A[EDGE_INSN: B:153:0x02a2->B:108:0x02a2 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r21) throws com.google.android.exoplayer2.ParserException {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.f
            com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.t
            com.google.android.exoplayer2.util.Util.castNonNull(r2)
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.g
            com.google.android.exoplayer2.util.Util.castNonNull(r2)
        L_0x0013:
            int r2 = r21.bytesLeft()
            if (r2 <= 0) goto L_0x031b
            int r2 = r0.h
            r4 = -1
            r5 = 13
            r6 = 256(0x100, float:3.59E-43)
            r7 = 4
            r8 = 2
            r9 = 3
            r10 = 1
            r11 = 0
            com.google.android.exoplayer2.util.ParsableByteArray r12 = r0.c
            com.google.android.exoplayer2.util.ParsableBitArray r13 = r0.b
            if (r2 == 0) goto L_0x01bb
            if (r2 == r10) goto L_0x017c
            r4 = 10
            if (r2 == r8) goto L_0x013e
            if (r2 == r9) goto L_0x0072
            if (r2 != r7) goto L_0x006c
            int r2 = r21.bytesLeft()
            int r3 = r0.r
            int r4 = r0.i
            int r3 = r3 - r4
            int r2 = java.lang.Math.min(r2, r3)
            com.google.android.exoplayer2.extractor.TrackOutput r3 = r0.t
            r3.sampleData(r1, r2)
            int r3 = r0.i
            int r3 = r3 + r2
            r0.i = r3
            int r2 = r0.r
            if (r3 != r2) goto L_0x0013
            com.google.android.exoplayer2.extractor.TrackOutput r12 = r0.t
            long r13 = r0.s
            r15 = 1
            r17 = 0
            r18 = 0
            r16 = r2
            r12.sampleMetadata(r13, r15, r16, r17, r18)
            long r2 = r0.s
            long r4 = r0.u
            long r2 = r2 + r4
            r0.s = r2
            r0.h = r11
            r0.i = r11
            r0.j = r6
            goto L_0x0013
        L_0x006c:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x0072:
            boolean r2 = r0.k
            r6 = 5
            if (r2 == 0) goto L_0x0079
            r3 = 7
            goto L_0x007a
        L_0x0079:
            r3 = 5
        L_0x007a:
            byte[] r2 = r13.a
            int r12 = r21.bytesLeft()
            int r14 = r0.i
            int r14 = r3 - r14
            int r12 = java.lang.Math.min(r12, r14)
            int r14 = r0.i
            r1.readBytes(r2, r14, r12)
            int r2 = r0.i
            int r2 = r2 + r12
            r0.i = r2
            if (r2 != r3) goto L_0x0096
            r2 = 1
            goto L_0x0097
        L_0x0096:
            r2 = 0
        L_0x0097:
            if (r2 == 0) goto L_0x0013
            r13.setPosition(r11)
            boolean r2 = r0.p
            if (r2 != 0) goto L_0x011c
            int r2 = r13.readBits(r8)
            int r2 = r2 + r10
            if (r2 == r8) goto L_0x00c5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r4 = 61
            r3.<init>(r4)
            java.lang.String r4 = "Detected audio object type: "
            r3.append(r4)
            r3.append(r2)
            java.lang.String r2 = ", but assuming AAC LC."
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            java.lang.String r3 = "AdtsReader"
            com.google.android.exoplayer2.util.Log.w(r3, r2)
            r2 = 2
        L_0x00c5:
            r13.skipBits(r6)
            int r3 = r13.readBits(r9)
            int r4 = r0.n
            byte[] r2 = com.google.android.exoplayer2.audio.AacUtil.buildAudioSpecificConfig(r2, r4, r3)
            com.google.android.exoplayer2.audio.AacUtil$Config r3 = com.google.android.exoplayer2.audio.AacUtil.parseAudioSpecificConfig(r2)
            com.google.android.exoplayer2.Format$Builder r4 = new com.google.android.exoplayer2.Format$Builder
            r4.<init>()
            java.lang.String r9 = r0.e
            com.google.android.exoplayer2.Format$Builder r4 = r4.setId((java.lang.String) r9)
            java.lang.String r9 = "audio/mp4a-latm"
            com.google.android.exoplayer2.Format$Builder r4 = r4.setSampleMimeType(r9)
            java.lang.String r9 = r3.c
            com.google.android.exoplayer2.Format$Builder r4 = r4.setCodecs(r9)
            int r9 = r3.b
            com.google.android.exoplayer2.Format$Builder r4 = r4.setChannelCount(r9)
            int r3 = r3.a
            com.google.android.exoplayer2.Format$Builder r3 = r4.setSampleRate(r3)
            java.util.List r2 = java.util.Collections.singletonList(r2)
            com.google.android.exoplayer2.Format$Builder r2 = r3.setInitializationData(r2)
            java.lang.String r3 = r0.d
            com.google.android.exoplayer2.Format$Builder r2 = r2.setLanguage(r3)
            com.google.android.exoplayer2.Format r2 = r2.build()
            int r3 = r2.ad
            long r3 = (long) r3
            r14 = 1024000000(0x3d090000, double:5.059232213E-315)
            long r14 = r14 / r3
            r0.q = r14
            com.google.android.exoplayer2.extractor.TrackOutput r3 = r0.f
            r3.format(r2)
            r0.p = r10
            goto L_0x011f
        L_0x011c:
            r13.skipBits(r4)
        L_0x011f:
            r13.skipBits(r7)
            int r2 = r13.readBits(r5)
            int r2 = r2 - r8
            int r2 = r2 - r6
            boolean r3 = r0.k
            if (r3 == 0) goto L_0x012e
            int r2 = r2 + -2
        L_0x012e:
            com.google.android.exoplayer2.extractor.TrackOutput r3 = r0.f
            long r4 = r0.q
            r0.h = r7
            r0.i = r11
            r0.t = r3
            r0.u = r4
            r0.r = r2
            goto L_0x0013
        L_0x013e:
            byte[] r2 = r12.getData()
            int r3 = r21.bytesLeft()
            int r5 = r0.i
            int r5 = 10 - r5
            int r3 = java.lang.Math.min(r3, r5)
            int r5 = r0.i
            r1.readBytes(r2, r5, r3)
            int r2 = r0.i
            int r2 = r2 + r3
            r0.i = r2
            if (r2 != r4) goto L_0x015b
            goto L_0x015c
        L_0x015b:
            r10 = 0
        L_0x015c:
            if (r10 == 0) goto L_0x0013
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.g
            r2.sampleData(r12, r4)
            r2 = 6
            r12.setPosition(r2)
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r0.g
            int r3 = r12.readSynchSafeInt()
            int r3 = r3 + r4
            r0.h = r7
            r0.i = r4
            r0.t = r2
            r4 = 0
            r0.u = r4
            r0.r = r3
            goto L_0x0013
        L_0x017c:
            int r2 = r21.bytesLeft()
            if (r2 != 0) goto L_0x0184
            goto L_0x0013
        L_0x0184:
            byte[] r2 = r13.a
            byte[] r3 = r21.getData()
            int r5 = r21.getPosition()
            byte r3 = r3[r5]
            r2[r11] = r3
            r13.setPosition(r8)
            int r2 = r13.readBits(r7)
            int r3 = r0.n
            if (r3 == r4) goto L_0x01a9
            if (r2 == r3) goto L_0x01a9
            r0.l = r11
            r0.h = r11
            r0.i = r11
            r0.j = r6
            goto L_0x0013
        L_0x01a9:
            boolean r3 = r0.l
            if (r3 != 0) goto L_0x01b5
            r0.l = r10
            int r3 = r0.o
            r0.m = r3
            r0.n = r2
        L_0x01b5:
            r0.h = r9
            r0.i = r11
            goto L_0x0013
        L_0x01bb:
            byte[] r2 = r21.getData()
            int r14 = r21.getPosition()
            int r15 = r21.limit()
        L_0x01c7:
            if (r14 >= r15) goto L_0x0316
            int r6 = r14 + 1
            byte r14 = r2[r14]
            r14 = r14 & 255(0xff, float:3.57E-43)
            int r9 = r0.j
            r3 = 512(0x200, float:7.175E-43)
            if (r9 != r3) goto L_0x02c4
            byte r9 = (byte) r14
            r9 = r9 & 255(0xff, float:3.57E-43)
            r19 = 65280(0xff00, float:9.1477E-41)
            r9 = r9 | r19
            boolean r9 = isAdtsSyncWord(r9)
            if (r9 == 0) goto L_0x02c4
            boolean r9 = r0.l
            if (r9 != 0) goto L_0x02a2
            int r9 = r6 + -2
            int r3 = r9 + 1
            r1.setPosition(r3)
            byte[] r3 = r13.a
            int r5 = r21.bytesLeft()
            if (r5 >= r10) goto L_0x01f8
            r3 = 0
            goto L_0x01fc
        L_0x01f8:
            r1.readBytes(r3, r11, r10)
            r3 = 1
        L_0x01fc:
            if (r3 != 0) goto L_0x01ff
            goto L_0x022d
        L_0x01ff:
            r13.setPosition(r7)
            int r3 = r13.readBits(r10)
            int r5 = r0.m
            if (r5 == r4) goto L_0x020d
            if (r3 == r5) goto L_0x020d
            goto L_0x022d
        L_0x020d:
            int r5 = r0.n
            if (r5 == r4) goto L_0x0235
            byte[] r5 = r13.a
            int r4 = r21.bytesLeft()
            if (r4 >= r10) goto L_0x021b
            r4 = 0
            goto L_0x021f
        L_0x021b:
            r1.readBytes(r5, r11, r10)
            r4 = 1
        L_0x021f:
            if (r4 != 0) goto L_0x0222
            goto L_0x0245
        L_0x0222:
            r13.setPosition(r8)
            int r4 = r13.readBits(r7)
            int r5 = r0.n
            if (r4 == r5) goto L_0x0230
        L_0x022d:
            r7 = -1
            goto L_0x029f
        L_0x0230:
            int r4 = r9 + 2
            r1.setPosition(r4)
        L_0x0235:
            byte[] r4 = r13.a
            int r5 = r21.bytesLeft()
            if (r5 >= r7) goto L_0x023f
            r4 = 0
            goto L_0x0243
        L_0x023f:
            r1.readBytes(r4, r11, r7)
            r4 = 1
        L_0x0243:
            if (r4 != 0) goto L_0x0247
        L_0x0245:
            r7 = -1
            goto L_0x029d
        L_0x0247:
            r4 = 14
            r13.setPosition(r4)
            r4 = 13
            int r5 = r13.readBits(r4)
            r4 = 7
            if (r5 >= r4) goto L_0x0256
            goto L_0x022d
        L_0x0256:
            byte[] r18 = r21.getData()
            int r4 = r21.limit()
            int r9 = r9 + r5
            if (r9 < r4) goto L_0x0262
            goto L_0x0245
        L_0x0262:
            byte r5 = r18[r9]
            r7 = -1
            if (r5 != r7) goto L_0x0281
            int r9 = r9 + 1
            if (r9 != r4) goto L_0x026c
            goto L_0x029d
        L_0x026c:
            byte r4 = r18[r9]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r4 = r4 | r19
            boolean r4 = isAdtsSyncWord(r4)
            if (r4 == 0) goto L_0x029f
            byte r4 = r18[r9]
            r4 = r4 & 8
            r5 = 3
            int r4 = r4 >> r5
            if (r4 != r3) goto L_0x029f
            goto L_0x029d
        L_0x0281:
            r3 = 73
            if (r5 == r3) goto L_0x0286
            goto L_0x029f
        L_0x0286:
            int r3 = r9 + 1
            if (r3 != r4) goto L_0x028b
            goto L_0x029d
        L_0x028b:
            byte r3 = r18[r3]
            r5 = 68
            if (r3 == r5) goto L_0x0292
            goto L_0x029f
        L_0x0292:
            int r9 = r9 + 2
            if (r9 != r4) goto L_0x0297
            goto L_0x029d
        L_0x0297:
            byte r3 = r18[r9]
            r4 = 51
            if (r3 != r4) goto L_0x029f
        L_0x029d:
            r3 = 1
            goto L_0x02a0
        L_0x029f:
            r3 = 0
        L_0x02a0:
            if (r3 == 0) goto L_0x02c5
        L_0x02a2:
            r2 = r14 & 8
            r3 = 3
            int r2 = r2 >> r3
            r0.o = r2
            r2 = r14 & 1
            if (r2 != 0) goto L_0x02ae
            r2 = 1
            goto L_0x02af
        L_0x02ae:
            r2 = 0
        L_0x02af:
            r0.k = r2
            boolean r2 = r0.l
            if (r2 != 0) goto L_0x02ba
            r0.h = r10
            r0.i = r11
            goto L_0x02bf
        L_0x02ba:
            r2 = 3
            r0.h = r2
            r0.i = r11
        L_0x02bf:
            r1.setPosition(r6)
            goto L_0x0013
        L_0x02c4:
            r7 = -1
        L_0x02c5:
            int r3 = r0.j
            r4 = r14 | r3
            r5 = 329(0x149, float:4.61E-43)
            if (r4 == r5) goto L_0x0305
            r5 = 511(0x1ff, float:7.16E-43)
            if (r4 == r5) goto L_0x02fd
            r5 = 836(0x344, float:1.171E-42)
            if (r4 == r5) goto L_0x02f5
            r5 = 1075(0x433, float:1.506E-42)
            if (r4 == r5) goto L_0x02e6
            r4 = 256(0x100, float:3.59E-43)
            if (r3 == r4) goto L_0x02e4
            r0.j = r4
            int r6 = r6 + -1
            r14 = r6
            r3 = 3
            goto L_0x030d
        L_0x02e4:
            r3 = 3
            goto L_0x030c
        L_0x02e6:
            r0.h = r8
            r3 = 3
            r0.i = r3
            r0.r = r11
            r12.setPosition(r11)
            r1.setPosition(r6)
            goto L_0x0013
        L_0x02f5:
            r3 = 3
            r4 = 256(0x100, float:3.59E-43)
            r5 = 1024(0x400, float:1.435E-42)
            r0.j = r5
            goto L_0x030c
        L_0x02fd:
            r3 = 3
            r4 = 256(0x100, float:3.59E-43)
            r5 = 512(0x200, float:7.175E-43)
            r0.j = r5
            goto L_0x030c
        L_0x0305:
            r3 = 3
            r4 = 256(0x100, float:3.59E-43)
            r5 = 768(0x300, float:1.076E-42)
            r0.j = r5
        L_0x030c:
            r14 = r6
        L_0x030d:
            r4 = -1
            r5 = 13
            r6 = 256(0x100, float:3.59E-43)
            r7 = 4
            r9 = 3
            goto L_0x01c7
        L_0x0316:
            r1.setPosition(r14)
            goto L_0x0013
        L_0x031b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.AdtsReader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.e = trackIdGenerator.getFormatId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
        this.f = track;
        this.t = track;
        if (this.a) {
            trackIdGenerator.generateNewId();
            TrackOutput track2 = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
            this.g = track2;
            track2.format(new Format.Builder().setId(trackIdGenerator.getFormatId()).setSampleMimeType("application/id3").build());
            return;
        }
        this.g = new DummyTrackOutput();
    }

    public long getSampleDurationUs() {
        return this.q;
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.s = j2;
    }

    public void seek() {
        this.l = false;
        this.h = 0;
        this.i = 0;
        this.j = 256;
    }

    public AdtsReader(boolean z, @Nullable String str) {
        this.b = new ParsableBitArray(new byte[7]);
        this.c = new ParsableByteArray(Arrays.copyOf(v, 10));
        this.h = 0;
        this.i = 0;
        this.j = 256;
        this.m = -1;
        this.n = -1;
        this.q = -9223372036854775807L;
        this.a = z;
        this.d = str;
    }
}
