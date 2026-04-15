package com.google.android.exoplayer2.extractor.flv;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

public final class FlvExtractor implements Extractor {
    public final ParsableByteArray a = new ParsableByteArray(4);
    public final ParsableByteArray b = new ParsableByteArray(9);
    public final ParsableByteArray c = new ParsableByteArray(11);
    public final ParsableByteArray d = new ParsableByteArray();
    public final ma e = new ma();
    public ExtractorOutput f;
    public int g = 1;
    public boolean h;
    public long i;
    public int j;
    public int k;
    public int l;
    public long m;
    public boolean n;
    public a o;
    public b p;

    public final ParsableByteArray a(ExtractorInput extractorInput) throws IOException {
        int i2 = this.l;
        ParsableByteArray parsableByteArray = this.d;
        if (i2 > parsableByteArray.capacity()) {
            parsableByteArray.reset(new byte[Math.max(parsableByteArray.capacity() * 2, this.l)], 0);
        } else {
            parsableByteArray.setPosition(0);
        }
        parsableByteArray.setLimit(this.l);
        extractorInput.readFully(parsableByteArray.getData(), 0, this.l);
        return parsableByteArray;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.f = extractorOutput;
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00d2 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0009 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r17, com.google.android.exoplayer2.extractor.PositionHolder r18) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.f
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r2)
        L_0x0009:
            int r2 = r0.g
            r3 = 4
            r4 = 0
            r5 = 1
            r6 = -1
            r7 = 8
            r8 = 9
            r9 = 2
            if (r2 == r5) goto L_0x0122
            r10 = 3
            if (r2 == r9) goto L_0x0117
            if (r2 == r10) goto L_0x00d9
            if (r2 != r3) goto L_0x00d3
            boolean r2 = r0.h
            r12 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            ma r6 = r0.e
            if (r2 == 0) goto L_0x002e
            long r14 = r0.i
            long r10 = r0.m
            long r14 = r14 + r10
            goto L_0x003b
        L_0x002e:
            long r10 = r6.getDurationUs()
            int r2 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r2 != 0) goto L_0x0039
            r14 = 0
            goto L_0x003b
        L_0x0039:
            long r14 = r0.m
        L_0x003b:
            int r2 = r0.k
            if (r2 != r7) goto L_0x005e
            com.google.android.exoplayer2.extractor.flv.a r7 = r0.o
            if (r7 == 0) goto L_0x005e
            boolean r2 = r0.n
            if (r2 != 0) goto L_0x0053
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.f
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r7 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            r7.<init>(r12)
            r2.seekMap(r7)
            r0.n = r5
        L_0x0053:
            com.google.android.exoplayer2.extractor.flv.a r2 = r0.o
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r16.a(r17)
            boolean r2 = r2.consume(r7, r14)
            goto L_0x00ab
        L_0x005e:
            if (r2 != r8) goto L_0x007f
            com.google.android.exoplayer2.extractor.flv.b r7 = r0.p
            if (r7 == 0) goto L_0x007f
            boolean r2 = r0.n
            if (r2 != 0) goto L_0x0074
            com.google.android.exoplayer2.extractor.ExtractorOutput r2 = r0.f
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r7 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            r7.<init>(r12)
            r2.seekMap(r7)
            r0.n = r5
        L_0x0074:
            com.google.android.exoplayer2.extractor.flv.b r2 = r0.p
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r16.a(r17)
            boolean r2 = r2.consume(r7, r14)
            goto L_0x00ab
        L_0x007f:
            r7 = 18
            if (r2 != r7) goto L_0x00ad
            boolean r2 = r0.n
            if (r2 != 0) goto L_0x00ad
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r16.a(r17)
            boolean r2 = r6.consume(r2, r14)
            long r7 = r6.getDurationUs()
            int r10 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r10 == 0) goto L_0x00ab
            com.google.android.exoplayer2.extractor.ExtractorOutput r10 = r0.f
            com.google.android.exoplayer2.extractor.IndexSeekMap r11 = new com.google.android.exoplayer2.extractor.IndexSeekMap
            long[] r14 = r6.getKeyFrameTagPositions()
            long[] r15 = r6.getKeyFrameTimesUs()
            r11.<init>(r14, r15, r7)
            r10.seekMap(r11)
            r0.n = r5
        L_0x00ab:
            r7 = 1
            goto L_0x00b4
        L_0x00ad:
            int r2 = r0.l
            r1.skipFully(r2)
            r2 = 0
            r7 = 0
        L_0x00b4:
            boolean r8 = r0.h
            if (r8 != 0) goto L_0x00cc
            if (r2 == 0) goto L_0x00cc
            r0.h = r5
            long r5 = r6.getDurationUs()
            int r2 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r2 != 0) goto L_0x00c8
            long r5 = r0.m
            long r10 = -r5
            goto L_0x00ca
        L_0x00c8:
            r10 = 0
        L_0x00ca:
            r0.i = r10
        L_0x00cc:
            r0.j = r3
            r0.g = r9
            if (r7 == 0) goto L_0x0009
            return r4
        L_0x00d3:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x00d9:
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r0.c
            byte[] r7 = r2.getData()
            r8 = 11
            boolean r7 = r1.readFully(r7, r4, r8, r5)
            if (r7 != 0) goto L_0x00e8
            goto L_0x0114
        L_0x00e8:
            r2.setPosition(r4)
            int r4 = r2.readUnsignedByte()
            r0.k = r4
            int r4 = r2.readUnsignedInt24()
            r0.l = r4
            int r4 = r2.readUnsignedInt24()
            long r7 = (long) r4
            r0.m = r7
            int r4 = r2.readUnsignedByte()
            int r4 = r4 << 24
            long r7 = (long) r4
            long r11 = r0.m
            long r7 = r7 | r11
            r11 = 1000(0x3e8, double:4.94E-321)
            long r7 = r7 * r11
            r0.m = r7
            r2.skipBytes(r10)
            r0.g = r3
            r4 = 1
        L_0x0114:
            if (r4 != 0) goto L_0x0009
            return r6
        L_0x0117:
            int r2 = r0.j
            r1.skipFully(r2)
            r0.j = r4
            r0.g = r10
            goto L_0x0009
        L_0x0122:
            com.google.android.exoplayer2.util.ParsableByteArray r2 = r0.b
            byte[] r10 = r2.getData()
            boolean r10 = r1.readFully(r10, r4, r8, r5)
            if (r10 != 0) goto L_0x012f
            goto L_0x017b
        L_0x012f:
            r2.setPosition(r4)
            r2.skipBytes(r3)
            int r10 = r2.readUnsignedByte()
            r11 = r10 & 4
            if (r11 == 0) goto L_0x013f
            r11 = 1
            goto L_0x0140
        L_0x013f:
            r11 = 0
        L_0x0140:
            r10 = r10 & 1
            if (r10 == 0) goto L_0x0145
            r4 = 1
        L_0x0145:
            if (r11 == 0) goto L_0x0158
            com.google.android.exoplayer2.extractor.flv.a r10 = r0.o
            if (r10 != 0) goto L_0x0158
            com.google.android.exoplayer2.extractor.flv.a r10 = new com.google.android.exoplayer2.extractor.flv.a
            com.google.android.exoplayer2.extractor.ExtractorOutput r11 = r0.f
            com.google.android.exoplayer2.extractor.TrackOutput r7 = r11.track(r7, r5)
            r10.<init>(r7)
            r0.o = r10
        L_0x0158:
            if (r4 == 0) goto L_0x016b
            com.google.android.exoplayer2.extractor.flv.b r4 = r0.p
            if (r4 != 0) goto L_0x016b
            com.google.android.exoplayer2.extractor.flv.b r4 = new com.google.android.exoplayer2.extractor.flv.b
            com.google.android.exoplayer2.extractor.ExtractorOutput r7 = r0.f
            com.google.android.exoplayer2.extractor.TrackOutput r7 = r7.track(r8, r9)
            r4.<init>(r7)
            r0.p = r4
        L_0x016b:
            com.google.android.exoplayer2.extractor.ExtractorOutput r4 = r0.f
            r4.endTracks()
            int r2 = r2.readInt()
            int r2 = r2 - r8
            int r2 = r2 + r3
            r0.j = r2
            r0.g = r9
            r4 = 1
        L_0x017b:
            if (r4 != 0) goto L_0x0009
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.flv.FlvExtractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        if (j2 == 0) {
            this.g = 1;
            this.h = false;
        } else {
            this.g = 3;
        }
        this.j = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        ParsableByteArray parsableByteArray = this.a;
        extractorInput.peekFully(parsableByteArray.getData(), 0, 3);
        parsableByteArray.setPosition(0);
        if (parsableByteArray.readUnsignedInt24() != 4607062) {
            return false;
        }
        extractorInput.peekFully(parsableByteArray.getData(), 0, 2);
        parsableByteArray.setPosition(0);
        if ((parsableByteArray.readUnsignedShort() & ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) != 0) {
            return false;
        }
        extractorInput.peekFully(parsableByteArray.getData(), 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        extractorInput.resetPeekPosition();
        extractorInput.advancePeekPosition(readInt);
        extractorInput.peekFully(parsableByteArray.getData(), 0, 4);
        parsableByteArray.setPosition(0);
        if (parsableByteArray.readInt() == 0) {
            return true;
        }
        return false;
    }
}
