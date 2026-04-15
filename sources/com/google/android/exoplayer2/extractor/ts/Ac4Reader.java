package com.google.android.exoplayer2.extractor.ts;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class Ac4Reader implements ElementaryStreamReader {
    public final ParsableBitArray a;
    public final ParsableByteArray b;
    @Nullable
    public final String c;
    public String d;
    public TrackOutput e;
    public int f;
    public int g;
    public boolean h;
    public boolean i;
    public long j;
    public Format k;
    public int l;
    public long m;

    public Ac4Reader() {
        this((String) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consume(com.google.android.exoplayer2.util.ParsableByteArray r12) {
        /*
            r11 = this;
            com.google.android.exoplayer2.extractor.TrackOutput r0 = r11.e
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r0)
        L_0x0005:
            int r0 = r12.bytesLeft()
            if (r0 <= 0) goto L_0x012c
            int r0 = r11.f
            r1 = 2
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r11.b
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L_0x00d9
            if (r0 == r4) goto L_0x0048
            if (r0 == r1) goto L_0x0019
            goto L_0x0005
        L_0x0019:
            int r0 = r12.bytesLeft()
            int r1 = r11.l
            int r2 = r11.g
            int r1 = r1 - r2
            int r0 = java.lang.Math.min(r0, r1)
            com.google.android.exoplayer2.extractor.TrackOutput r1 = r11.e
            r1.sampleData(r12, r0)
            int r1 = r11.g
            int r1 = r1 + r0
            r11.g = r1
            int r8 = r11.l
            if (r1 != r8) goto L_0x0005
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r11.e
            long r5 = r11.m
            r7 = 1
            r9 = 0
            r10 = 0
            r4.sampleMetadata(r5, r7, r8, r9, r10)
            long r0 = r11.m
            long r4 = r11.j
            long r0 = r0 + r4
            r11.m = r0
            r11.f = r3
            goto L_0x0005
        L_0x0048:
            byte[] r0 = r2.getData()
            int r5 = r12.bytesLeft()
            int r6 = r11.g
            r7 = 16
            int r6 = 16 - r6
            int r5 = java.lang.Math.min(r5, r6)
            int r6 = r11.g
            r12.readBytes(r0, r6, r5)
            int r0 = r11.g
            int r0 = r0 + r5
            r11.g = r0
            if (r0 != r7) goto L_0x0067
            goto L_0x0068
        L_0x0067:
            r4 = 0
        L_0x0068:
            if (r4 == 0) goto L_0x0005
            com.google.android.exoplayer2.util.ParsableBitArray r0 = r11.a
            r0.setPosition(r3)
            com.google.android.exoplayer2.audio.Ac4Util$SyncFrameInfo r0 = com.google.android.exoplayer2.audio.Ac4Util.parseAc4SyncframeInfo(r0)
            com.google.android.exoplayer2.Format r4 = r11.k
            java.lang.String r5 = "audio/ac4"
            if (r4 == 0) goto L_0x008d
            int r6 = r0.b
            int r8 = r4.ac
            if (r6 != r8) goto L_0x008d
            int r6 = r0.a
            int r8 = r4.ad
            if (r6 != r8) goto L_0x008d
            java.lang.String r4 = r4.p
            boolean r4 = r5.equals(r4)
            if (r4 != 0) goto L_0x00b9
        L_0x008d:
            com.google.android.exoplayer2.Format$Builder r4 = new com.google.android.exoplayer2.Format$Builder
            r4.<init>()
            java.lang.String r6 = r11.d
            com.google.android.exoplayer2.Format$Builder r4 = r4.setId((java.lang.String) r6)
            com.google.android.exoplayer2.Format$Builder r4 = r4.setSampleMimeType(r5)
            int r5 = r0.b
            com.google.android.exoplayer2.Format$Builder r4 = r4.setChannelCount(r5)
            int r5 = r0.a
            com.google.android.exoplayer2.Format$Builder r4 = r4.setSampleRate(r5)
            java.lang.String r5 = r11.c
            com.google.android.exoplayer2.Format$Builder r4 = r4.setLanguage(r5)
            com.google.android.exoplayer2.Format r4 = r4.build()
            r11.k = r4
            com.google.android.exoplayer2.extractor.TrackOutput r5 = r11.e
            r5.format(r4)
        L_0x00b9:
            int r4 = r0.c
            r11.l = r4
            int r0 = r0.d
            long r4 = (long) r0
            r8 = 1000000(0xf4240, double:4.940656E-318)
            long r4 = r4 * r8
            com.google.android.exoplayer2.Format r0 = r11.k
            int r0 = r0.ad
            long r8 = (long) r0
            long r4 = r4 / r8
            r11.j = r4
            r2.setPosition(r3)
            com.google.android.exoplayer2.extractor.TrackOutput r0 = r11.e
            r0.sampleData(r2, r7)
            r11.f = r1
            goto L_0x0005
        L_0x00d9:
            int r0 = r12.bytesLeft()
            r5 = 65
            r6 = 64
            if (r0 <= 0) goto L_0x010d
            boolean r0 = r11.h
            r7 = 172(0xac, float:2.41E-43)
            if (r0 != 0) goto L_0x00f5
            int r0 = r12.readUnsignedByte()
            if (r0 != r7) goto L_0x00f1
            r0 = 1
            goto L_0x00f2
        L_0x00f1:
            r0 = 0
        L_0x00f2:
            r11.h = r0
            goto L_0x00d9
        L_0x00f5:
            int r0 = r12.readUnsignedByte()
            if (r0 != r7) goto L_0x00fd
            r7 = 1
            goto L_0x00fe
        L_0x00fd:
            r7 = 0
        L_0x00fe:
            r11.h = r7
            if (r0 == r6) goto L_0x0104
            if (r0 != r5) goto L_0x00d9
        L_0x0104:
            if (r0 != r5) goto L_0x0108
            r0 = 1
            goto L_0x0109
        L_0x0108:
            r0 = 0
        L_0x0109:
            r11.i = r0
            r0 = 1
            goto L_0x010e
        L_0x010d:
            r0 = 0
        L_0x010e:
            if (r0 == 0) goto L_0x0005
            r11.f = r4
            byte[] r0 = r2.getData()
            r7 = -84
            r0[r3] = r7
            byte[] r0 = r2.getData()
            boolean r2 = r11.i
            if (r2 == 0) goto L_0x0123
            goto L_0x0125
        L_0x0123:
            r5 = 64
        L_0x0125:
            byte r2 = (byte) r5
            r0[r4] = r2
            r11.g = r1
            goto L_0x0005
        L_0x012c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.ts.Ac4Reader.consume(com.google.android.exoplayer2.util.ParsableByteArray):void");
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.d = trackIdGenerator.getFormatId();
        this.e = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    public void packetFinished() {
    }

    public void packetStarted(long j2, int i2) {
        this.m = j2;
    }

    public void seek() {
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = false;
    }

    public Ac4Reader(@Nullable String str) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(new byte[16]);
        this.a = parsableBitArray;
        this.b = new ParsableByteArray(parsableBitArray.a);
        this.f = 0;
        this.g = 0;
        this.h = false;
        this.i = false;
        this.c = str;
    }
}
