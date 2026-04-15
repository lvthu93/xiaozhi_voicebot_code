package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

/* renamed from: d1  reason: default package */
public final class d1 implements n7 {
    public final m7 a;
    public final long b;
    public final long c;
    public final pb d;
    public int e;
    public long f;
    public long g;
    public long h;
    public long i;
    public long j;
    public long k;
    public long l;

    /* renamed from: d1$a */
    public final class a implements SeekMap {
        public a() {
        }

        public long getDurationUs() {
            d1 d1Var = d1.this;
            return (d1Var.f * 1000000) / ((long) d1Var.d.i);
        }

        public SeekMap.SeekPoints getSeekPoints(long j) {
            d1 d1Var = d1.this;
            long j2 = d1Var.b;
            long j3 = d1Var.c;
            return new SeekMap.SeekPoints(new SeekPoint(j, Util.constrainValue(((((j3 - j2) * ((((long) d1Var.d.i) * j) / 1000000)) / d1Var.f) + j2) - 30000, j2, j3 - 1)));
        }

        public boolean isSeekable() {
            return true;
        }
    }

    public d1(pb pbVar, long j2, long j3, long j4, long j5, boolean z) {
        boolean z2;
        if (j2 < 0 || j3 <= j2) {
            z2 = false;
        } else {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        this.d = pbVar;
        this.b = j2;
        this.c = j3;
        if (j4 == j3 - j2 || z) {
            this.f = j5;
            this.e = 4;
        } else {
            this.e = 0;
        }
        this.a = new m7();
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00c7 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long read(com.google.android.exoplayer2.extractor.ExtractorInput r25) throws java.io.IOException {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            int r2 = r0.e
            long r3 = r0.c
            r5 = 0
            m7 r6 = r0.a
            r7 = 1
            r8 = 4
            if (r2 == 0) goto L_0x00fe
            if (r2 == r7) goto L_0x00fc
            r3 = 2
            r4 = 3
            r11 = -1
            if (r2 == r3) goto L_0x0026
            if (r2 == r4) goto L_0x0022
            if (r2 != r8) goto L_0x001c
            return r11
        L_0x001c:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            throw r1
        L_0x0022:
            r23 = r6
            goto L_0x00ca
        L_0x0026:
            long r2 = r0.i
            long r13 = r0.j
            int r7 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
            if (r7 != 0) goto L_0x0033
        L_0x002e:
            r23 = r6
            r13 = r11
            goto L_0x00c1
        L_0x0033:
            long r2 = r25.getPosition()
            long r13 = r0.j
            boolean r7 = r6.skipToNextPage(r1, r13)
            if (r7 != 0) goto L_0x0051
            long r13 = r0.i
            int r7 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r7 == 0) goto L_0x0049
            r23 = r6
            goto L_0x00c1
        L_0x0049:
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "No ogg page can be found."
            r1.<init>(r2)
            throw r1
        L_0x0051:
            r6.populate(r1, r5)
            r25.resetPeekPosition()
            long r13 = r0.h
            long r9 = r6.b
            long r13 = r13 - r9
            int r7 = r6.d
            int r15 = r6.e
            int r7 = r7 + r15
            r15 = 0
            int r17 = (r15 > r13 ? 1 : (r15 == r13 ? 0 : -1))
            if (r17 > 0) goto L_0x006f
            r17 = 72000(0x11940, double:3.55727E-319)
            int r19 = (r13 > r17 ? 1 : (r13 == r17 ? 0 : -1))
            if (r19 >= 0) goto L_0x006f
            goto L_0x002e
        L_0x006f:
            int r17 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r17 >= 0) goto L_0x0078
            r0.j = r2
            r0.l = r9
            goto L_0x0084
        L_0x0078:
            long r2 = r25.getPosition()
            long r9 = (long) r7
            long r2 = r2 + r9
            r0.i = r2
            long r2 = r6.b
            r0.k = r2
        L_0x0084:
            long r2 = r0.j
            long r9 = r0.i
            long r2 = r2 - r9
            r15 = 100000(0x186a0, double:4.94066E-319)
            int r18 = (r2 > r15 ? 1 : (r2 == r15 ? 0 : -1))
            if (r18 >= 0) goto L_0x0096
            r0.j = r9
            r23 = r6
            r13 = r9
            goto L_0x00c1
        L_0x0096:
            long r2 = (long) r7
            r9 = 1
            if (r17 > 0) goto L_0x009e
            r15 = 2
            goto L_0x009f
        L_0x009e:
            r15 = r9
        L_0x009f:
            long r2 = r2 * r15
            long r15 = r25.getPosition()
            long r15 = r15 - r2
            long r2 = r0.j
            r23 = r6
            long r5 = r0.i
            long r17 = r2 - r5
            long r17 = r17 * r13
            long r13 = r0.l
            long r11 = r0.k
            long r13 = r13 - r11
            long r17 = r17 / r13
            long r17 = r17 + r15
            long r21 = r2 - r9
            r19 = r5
            long r13 = com.google.android.exoplayer2.util.Util.constrainValue((long) r17, (long) r19, (long) r21)
        L_0x00c1:
            r2 = -1
            int r5 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r5 == 0) goto L_0x00c8
            return r13
        L_0x00c8:
            r0.e = r4
        L_0x00ca:
            r2 = r23
        L_0x00cc:
            r2.skipToNextPage(r1)
            r3 = 0
            r2.populate(r1, r3)
            long r3 = r2.b
            long r5 = r0.h
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 <= 0) goto L_0x00e7
            r25.resetPeekPosition()
            r0.e = r8
            long r1 = r0.k
            r5 = 2
            long r1 = r1 + r5
            long r1 = -r1
            return r1
        L_0x00e7:
            r5 = 2
            int r3 = r2.d
            int r4 = r2.e
            int r3 = r3 + r4
            r1.skipFully(r3)
            long r3 = r25.getPosition()
            r0.i = r3
            long r3 = r2.b
            r0.k = r3
            goto L_0x00cc
        L_0x00fc:
            r2 = r6
            goto L_0x0111
        L_0x00fe:
            r2 = r6
            long r5 = r25.getPosition()
            r0.g = r5
            r0.e = r7
            r9 = 65307(0xff1b, double:3.2266E-319)
            long r9 = r3 - r9
            int r11 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r11 <= 0) goto L_0x0111
            return r9
        L_0x0111:
            r2.reset()
            boolean r5 = r2.skipToNextPage(r1)
            if (r5 == 0) goto L_0x0157
            r5 = 0
            r2.populate(r1, r5)
            int r5 = r2.d
            int r6 = r2.e
            int r5 = r5 + r6
            r1.skipFully(r5)
            long r5 = r2.b
        L_0x0128:
            int r9 = r2.a
            r9 = r9 & r8
            if (r9 == r8) goto L_0x0150
            boolean r9 = r2.skipToNextPage(r1)
            if (r9 == 0) goto L_0x0150
            long r9 = r25.getPosition()
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x0150
            boolean r9 = r2.populate(r1, r7)
            if (r9 == 0) goto L_0x0150
            int r9 = r2.d
            int r10 = r2.e
            int r9 = r9 + r10
            boolean r9 = com.google.android.exoplayer2.extractor.ExtractorUtil.skipFullyQuietly(r1, r9)
            if (r9 != 0) goto L_0x014d
            goto L_0x0150
        L_0x014d:
            long r5 = r2.b
            goto L_0x0128
        L_0x0150:
            r0.f = r5
            r0.e = r8
            long r1 = r0.g
            return r1
        L_0x0157:
            java.io.EOFException r1 = new java.io.EOFException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.d1.read(com.google.android.exoplayer2.extractor.ExtractorInput):long");
    }

    public void startSeek(long j2) {
        this.h = Util.constrainValue(j2, 0, this.f - 1);
        this.e = 2;
        this.i = this.b;
        this.j = this.c;
        this.k = 0;
        this.l = this.f;
    }

    @Nullable
    public a createSeekMap() {
        if (this.f != 0) {
            return new a();
        }
        return null;
    }
}
