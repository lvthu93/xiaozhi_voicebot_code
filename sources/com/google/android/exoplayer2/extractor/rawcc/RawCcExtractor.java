package com.google.android.exoplayer2.extractor.rawcc;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

public final class RawCcExtractor implements Extractor {
    public final Format a;
    public final ParsableByteArray b = new ParsableByteArray(9);
    public TrackOutput c;
    public int d = 0;
    public int e;
    public long f;
    public int g;
    public int h;

    public RawCcExtractor(Format format) {
        this.a = format;
    }

    public void init(ExtractorOutput extractorOutput) {
        extractorOutput.seekMap(new SeekMap.Unseekable(-9223372036854775807L));
        TrackOutput track = extractorOutput.track(0, 3);
        this.c = track;
        track.format(this.a);
        extractorOutput.endTracks();
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0091 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r12, com.google.android.exoplayer2.extractor.PositionHolder r13) throws java.io.IOException {
        /*
            r11 = this;
            com.google.android.exoplayer2.extractor.TrackOutput r13 = r11.c
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r13)
        L_0x0005:
            int r13 = r11.d
            r0 = -1
            com.google.android.exoplayer2.util.ParsableByteArray r1 = r11.b
            r2 = 0
            r3 = 1
            if (r13 == 0) goto L_0x00a4
            r4 = 2
            if (r13 == r3) goto L_0x0049
            if (r13 != r4) goto L_0x0043
        L_0x0013:
            int r13 = r11.g
            if (r13 <= 0) goto L_0x0032
            r13 = 3
            r1.reset((int) r13)
            byte[] r4 = r1.getData()
            r12.readFully(r4, r2, r13)
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r11.c
            r4.sampleData(r1, r13)
            int r4 = r11.h
            int r4 = r4 + r13
            r11.h = r4
            int r13 = r11.g
            int r13 = r13 + r0
            r11.g = r13
            goto L_0x0013
        L_0x0032:
            int r8 = r11.h
            if (r8 <= 0) goto L_0x0040
            com.google.android.exoplayer2.extractor.TrackOutput r4 = r11.c
            long r5 = r11.f
            r7 = 1
            r9 = 0
            r10 = 0
            r4.sampleMetadata(r5, r7, r8, r9, r10)
        L_0x0040:
            r11.d = r3
            return r2
        L_0x0043:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            r12.<init>()
            throw r12
        L_0x0049:
            int r13 = r11.e
            if (r13 != 0) goto L_0x006a
            r13 = 5
            r1.reset((int) r13)
            byte[] r5 = r1.getData()
            boolean r13 = r12.readFully(r5, r2, r13, r3)
            if (r13 != 0) goto L_0x005c
            goto L_0x007b
        L_0x005c:
            long r5 = r1.readUnsignedInt()
            r7 = 1000(0x3e8, double:4.94E-321)
            long r5 = r5 * r7
            r7 = 45
            long r5 = r5 / r7
            r11.f = r5
            goto L_0x0083
        L_0x006a:
            if (r13 != r3) goto L_0x0094
            r13 = 9
            r1.reset((int) r13)
            byte[] r5 = r1.getData()
            boolean r13 = r12.readFully(r5, r2, r13, r3)
            if (r13 != 0) goto L_0x007d
        L_0x007b:
            r3 = 0
            goto L_0x008b
        L_0x007d:
            long r5 = r1.readLong()
            r11.f = r5
        L_0x0083:
            int r13 = r1.readUnsignedByte()
            r11.g = r13
            r11.h = r2
        L_0x008b:
            if (r3 == 0) goto L_0x0091
            r11.d = r4
            goto L_0x0005
        L_0x0091:
            r11.d = r2
            return r0
        L_0x0094:
            com.google.android.exoplayer2.ParserException r12 = new com.google.android.exoplayer2.ParserException
            int r13 = r11.e
            r0 = 39
            java.lang.String r1 = "Unsupported version number: "
            java.lang.String r13 = defpackage.y2.d(r0, r1, r13)
            r12.<init>((java.lang.String) r13)
            throw r12
        L_0x00a4:
            r13 = 8
            r1.reset((int) r13)
            byte[] r4 = r1.getData()
            boolean r13 = r12.readFully(r4, r2, r13, r3)
            if (r13 == 0) goto L_0x00cc
            int r13 = r1.readInt()
            r2 = 1380139777(0x52434301, float:2.0966069E11)
            if (r13 != r2) goto L_0x00c4
            int r13 = r1.readUnsignedByte()
            r11.e = r13
            r2 = 1
            goto L_0x00cc
        L_0x00c4:
            java.io.IOException r12 = new java.io.IOException
            java.lang.String r13 = "Input not RawCC"
            r12.<init>(r13)
            throw r12
        L_0x00cc:
            if (r2 == 0) goto L_0x00d2
            r11.d = r3
            goto L_0x0005
        L_0x00d2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j, long j2) {
        this.d = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.b;
        parsableByteArray.reset(8);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 8);
        if (parsableByteArray.readInt() == 1380139777) {
            return true;
        }
        return false;
    }
}
