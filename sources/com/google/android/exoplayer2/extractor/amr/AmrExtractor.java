package com.google.android.exoplayer2.extractor.amr;

import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class AmrExtractor implements Extractor {
    public static final int[] p = {13, 14, 16, 18, 20, 21, 27, 32, 6, 7, 6, 6, 1, 1, 1, 1};
    public static final int[] q;
    public static final byte[] r = Util.getUtf8Bytes("#!AMR\n");
    public static final byte[] s = Util.getUtf8Bytes("#!AMR-WB\n");
    public static final int t;
    public final byte[] a;
    public final int b;
    public boolean c;
    public long d;
    public int e;
    public int f;
    public boolean g;
    public long h;
    public int i;
    public int j;
    public long k;
    public ExtractorOutput l;
    public TrackOutput m;
    public SeekMap n;
    public boolean o;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    static {
        int[] iArr = {18, 24, 33, 37, 41, 47, 51, 59, 61, 6, 1, 1, 1, 1, 1, 1};
        q = iArr;
        t = iArr[8];
    }

    public AmrExtractor() {
        this(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
        if (r0 != false) goto L_0x0039;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int a(com.google.android.exoplayer2.extractor.ExtractorInput r5) throws java.io.IOException {
        /*
            r4 = this;
            r5.resetPeekPosition()
            byte[] r0 = r4.a
            r1 = 0
            r2 = 1
            r5.peekFully(r0, r1, r2)
            byte r5 = r0[r1]
            r0 = r5 & 131(0x83, float:1.84E-43)
            if (r0 > 0) goto L_0x0078
            int r5 = r5 >> 3
            r0 = 15
            r5 = r5 & r0
            if (r5 < 0) goto L_0x003a
            if (r5 > r0) goto L_0x003a
            boolean r0 = r4.c
            if (r0 == 0) goto L_0x0027
            r2 = 10
            if (r5 < r2) goto L_0x0025
            r2 = 13
            if (r5 <= r2) goto L_0x0027
        L_0x0025:
            r2 = 1
            goto L_0x0028
        L_0x0027:
            r2 = 0
        L_0x0028:
            if (r2 != 0) goto L_0x0039
            if (r0 != 0) goto L_0x0036
            r0 = 12
            if (r5 < r0) goto L_0x0034
            r0 = 14
            if (r5 <= r0) goto L_0x0036
        L_0x0034:
            r0 = 1
            goto L_0x0037
        L_0x0036:
            r0 = 0
        L_0x0037:
            if (r0 == 0) goto L_0x003a
        L_0x0039:
            r1 = 1
        L_0x003a:
            if (r1 != 0) goto L_0x006a
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            boolean r1 = r4.c
            if (r1 == 0) goto L_0x0045
            java.lang.String r1 = "WB"
            goto L_0x0047
        L_0x0045:
            java.lang.String r1 = "NB"
        L_0x0047:
            int r2 = r1.length()
            int r2 = r2 + 35
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Illegal AMR "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = " frame type "
            r3.append(r1)
            r3.append(r5)
            java.lang.String r5 = r3.toString()
            r0.<init>((java.lang.String) r5)
            throw r0
        L_0x006a:
            boolean r0 = r4.c
            if (r0 == 0) goto L_0x0073
            int[] r0 = q
            r5 = r0[r5]
            goto L_0x0077
        L_0x0073:
            int[] r0 = p
            r5 = r0[r5]
        L_0x0077:
            return r5
        L_0x0078:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            r1 = 42
            java.lang.String r2 = "Invalid padding bits for frame header "
            java.lang.String r5 = defpackage.y2.d(r1, r2, r5)
            r0.<init>((java.lang.String) r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.amr.AmrExtractor.a(com.google.android.exoplayer2.extractor.ExtractorInput):int");
    }

    public final boolean b(ExtractorInput extractorInput) throws IOException {
        extractorInput.resetPeekPosition();
        byte[] bArr = r;
        byte[] bArr2 = new byte[bArr.length];
        extractorInput.peekFully(bArr2, 0, bArr.length);
        if (Arrays.equals(bArr2, bArr)) {
            this.c = false;
            extractorInput.skipFully(bArr.length);
            return true;
        }
        extractorInput.resetPeekPosition();
        byte[] bArr3 = s;
        byte[] bArr4 = new byte[bArr3.length];
        extractorInput.peekFully(bArr4, 0, bArr3.length);
        if (!Arrays.equals(bArr4, bArr3)) {
            return false;
        }
        this.c = true;
        extractorInput.skipFully(bArr3.length);
        return true;
    }

    public void init(ExtractorOutput extractorOutput) {
        this.l = extractorOutput;
        this.m = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r13, com.google.android.exoplayer2.extractor.PositionHolder r14) throws java.io.IOException {
        /*
            r12 = this;
            com.google.android.exoplayer2.extractor.TrackOutput r14 = r12.m
            com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r14)
            com.google.android.exoplayer2.extractor.ExtractorOutput r14 = r12.l
            com.google.android.exoplayer2.util.Util.castNonNull(r14)
            long r0 = r13.getPosition()
            r2 = 0
            int r14 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r14 != 0) goto L_0x0023
            boolean r14 = r12.b(r13)
            if (r14 == 0) goto L_0x001b
            goto L_0x0023
        L_0x001b:
            com.google.android.exoplayer2.ParserException r13 = new com.google.android.exoplayer2.ParserException
            java.lang.String r14 = "Could not find AMR header."
            r13.<init>((java.lang.String) r14)
            throw r13
        L_0x0023:
            boolean r14 = r12.o
            r0 = 1
            if (r14 != 0) goto L_0x005a
            r12.o = r0
            boolean r14 = r12.c
            if (r14 == 0) goto L_0x0031
            java.lang.String r1 = "audio/amr-wb"
            goto L_0x0033
        L_0x0031:
            java.lang.String r1 = "audio/3gpp"
        L_0x0033:
            if (r14 == 0) goto L_0x0038
            r14 = 16000(0x3e80, float:2.2421E-41)
            goto L_0x003a
        L_0x0038:
            r14 = 8000(0x1f40, float:1.121E-41)
        L_0x003a:
            com.google.android.exoplayer2.extractor.TrackOutput r2 = r12.m
            com.google.android.exoplayer2.Format$Builder r3 = new com.google.android.exoplayer2.Format$Builder
            r3.<init>()
            com.google.android.exoplayer2.Format$Builder r1 = r3.setSampleMimeType(r1)
            int r3 = t
            com.google.android.exoplayer2.Format$Builder r1 = r1.setMaxInputSize(r3)
            com.google.android.exoplayer2.Format$Builder r1 = r1.setChannelCount(r0)
            com.google.android.exoplayer2.Format$Builder r14 = r1.setSampleRate(r14)
            com.google.android.exoplayer2.Format r14 = r14.build()
            r2.format(r14)
        L_0x005a:
            int r14 = r12.f
            r1 = 20000(0x4e20, double:9.8813E-320)
            r3 = -1
            if (r14 != 0) goto L_0x0082
            int r14 = r12.a(r13)     // Catch:{ EOFException -> 0x008c }
            r12.e = r14     // Catch:{ EOFException -> 0x008c }
            r12.f = r14
            int r14 = r12.i
            if (r14 != r3) goto L_0x0077
            long r4 = r13.getPosition()
            r12.h = r4
            int r14 = r12.e
            r12.i = r14
        L_0x0077:
            int r14 = r12.i
            int r4 = r12.e
            if (r14 != r4) goto L_0x0082
            int r14 = r12.j
            int r14 = r14 + r0
            r12.j = r14
        L_0x0082:
            com.google.android.exoplayer2.extractor.TrackOutput r14 = r12.m
            int r4 = r12.f
            int r14 = r14.sampleData((com.google.android.exoplayer2.upstream.DataReader) r13, (int) r4, (boolean) r0)
            if (r14 != r3) goto L_0x008e
        L_0x008c:
            r14 = -1
            goto L_0x00ab
        L_0x008e:
            int r4 = r12.f
            int r4 = r4 - r14
            r12.f = r4
            r14 = 0
            if (r4 <= 0) goto L_0x0097
            goto L_0x00ab
        L_0x0097:
            com.google.android.exoplayer2.extractor.TrackOutput r5 = r12.m
            long r6 = r12.k
            long r8 = r12.d
            long r6 = r6 + r8
            r8 = 1
            int r9 = r12.e
            r10 = 0
            r11 = 0
            r5.sampleMetadata(r6, r8, r9, r10, r11)
            long r4 = r12.d
            long r4 = r4 + r1
            r12.d = r4
        L_0x00ab:
            long r5 = r13.getLength()
            boolean r13 = r12.g
            if (r13 == 0) goto L_0x00b4
            goto L_0x00ff
        L_0x00b4:
            int r13 = r12.b
            r13 = r13 & r0
            if (r13 == 0) goto L_0x00ec
            r7 = -1
            int r13 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r13 == 0) goto L_0x00ec
            int r10 = r12.i
            if (r10 == r3) goto L_0x00c8
            int r13 = r12.e
            if (r10 == r13) goto L_0x00c8
            goto L_0x00ec
        L_0x00c8:
            int r13 = r12.j
            r4 = 20
            if (r13 >= r4) goto L_0x00d0
            if (r14 != r3) goto L_0x00ff
        L_0x00d0:
            int r13 = r10 * 8
            long r3 = (long) r13
            r7 = 1000000(0xf4240, double:4.940656E-318)
            long r3 = r3 * r7
            long r3 = r3 / r1
            int r9 = (int) r3
            com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap r13 = new com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap
            long r7 = r12.h
            r4 = r13
            r4.<init>(r5, r7, r9, r10)
            r12.n = r13
            com.google.android.exoplayer2.extractor.ExtractorOutput r1 = r12.l
            r1.seekMap(r13)
            r12.g = r0
            goto L_0x00ff
        L_0x00ec:
            com.google.android.exoplayer2.extractor.SeekMap$Unseekable r13 = new com.google.android.exoplayer2.extractor.SeekMap$Unseekable
            r1 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r13.<init>(r1)
            r12.n = r13
            com.google.android.exoplayer2.extractor.ExtractorOutput r1 = r12.l
            r1.seekMap(r13)
            r12.g = r0
        L_0x00ff:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.amr.AmrExtractor.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder):int");
    }

    public void release() {
    }

    public void seek(long j2, long j3) {
        this.d = 0;
        this.e = 0;
        this.f = 0;
        if (j2 != 0) {
            SeekMap seekMap = this.n;
            if (seekMap instanceof ConstantBitrateSeekMap) {
                this.k = ((ConstantBitrateSeekMap) seekMap).getTimeUsAtPosition(j2);
                return;
            }
        }
        this.k = 0;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        return b(extractorInput);
    }

    public AmrExtractor(int i2) {
        this.b = i2;
        this.a = new byte[1];
        this.i = -1;
    }
}
