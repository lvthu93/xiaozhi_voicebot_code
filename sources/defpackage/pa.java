package defpackage;

import com.google.common.base.Splitter;
import java.util.ArrayList;

/* renamed from: pa  reason: default package */
public final class pa {
    public static final Splitter d = Splitter.on(':');
    public static final Splitter e = Splitter.on('*');
    public final ArrayList a = new ArrayList();
    public int b = 0;
    public int c;

    /* renamed from: pa$a */
    public static final class a {
        public final long a;
        public final int b;

        public a(int i, long j, int i2) {
            this.a = j;
            this.b = i2;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(com.google.android.exoplayer2.extractor.ExtractorInput r30, com.google.android.exoplayer2.extractor.PositionHolder r31, java.util.List<com.google.android.exoplayer2.metadata.Metadata.Entry> r32) throws java.io.IOException {
        /*
            r29 = this;
            r1 = r29
            r0 = r30
            r2 = r31
            int r3 = r1.b
            r6 = 1
            if (r3 == 0) goto L_0x020d
            r7 = 2
            r9 = 0
            if (r3 == r6) goto L_0x01d9
            java.util.ArrayList r10 = r1.a
            r11 = 3
            r13 = 2192(0x890, float:3.072E-42)
            r14 = 2816(0xb00, float:3.946E-42)
            r15 = 2817(0xb01, float:3.947E-42)
            r8 = 2819(0xb03, float:3.95E-42)
            if (r3 == r7) goto L_0x0160
            if (r3 != r11) goto L_0x015a
            long r16 = r30.getPosition()
            long r18 = r30.getLength()
            long r20 = r30.getPosition()
            long r18 = r18 - r20
            int r3 = r1.c
            long r4 = (long) r3
            long r3 = r18 - r4
            int r4 = (int) r3
            com.google.android.exoplayer2.util.ParsableByteArray r3 = new com.google.android.exoplayer2.util.ParsableByteArray
            r3.<init>((int) r4)
            byte[] r5 = r3.getData()
            r0.readFully(r5, r9, r4)
            r0 = 0
        L_0x003f:
            int r4 = r10.size()
            if (r0 >= r4) goto L_0x0154
            java.lang.Object r4 = r10.get(r0)
            pa$a r4 = (defpackage.pa.a) r4
            r18 = r10
            long r9 = r4.a
            long r9 = r9 - r16
            int r10 = (int) r9
            r3.setPosition(r10)
            r9 = 4
            r3.skipBytes(r9)
            int r10 = r3.readLittleEndianInt()
            java.lang.String r5 = r3.readString(r10)
            r5.getClass()
            int r19 = r5.hashCode()
            switch(r19) {
                case -1711564334: goto L_0x0098;
                case -1332107749: goto L_0x008d;
                case -1251387154: goto L_0x0082;
                case -830665521: goto L_0x0077;
                case 1760745220: goto L_0x006c;
                default: goto L_0x006b;
            }
        L_0x006b:
            goto L_0x00a3
        L_0x006c:
            java.lang.String r12 = "Super_SlowMotion_BGM"
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x0075
            goto L_0x00a3
        L_0x0075:
            r5 = 4
            goto L_0x00a4
        L_0x0077:
            java.lang.String r12 = "Super_SlowMotion_Deflickering_On"
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x0080
            goto L_0x00a3
        L_0x0080:
            r5 = 3
            goto L_0x00a4
        L_0x0082:
            java.lang.String r12 = "Super_SlowMotion_Data"
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x008b
            goto L_0x00a3
        L_0x008b:
            r5 = 2
            goto L_0x00a4
        L_0x008d:
            java.lang.String r12 = "Super_SlowMotion_Edit_Data"
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x0096
            goto L_0x00a3
        L_0x0096:
            r5 = 1
            goto L_0x00a4
        L_0x0098:
            java.lang.String r12 = "SlowMotion_Data"
            boolean r5 = r5.equals(r12)
            if (r5 != 0) goto L_0x00a1
            goto L_0x00a3
        L_0x00a1:
            r5 = 0
            goto L_0x00a4
        L_0x00a3:
            r5 = -1
        L_0x00a4:
            if (r5 == 0) goto L_0x00c2
            if (r5 == r6) goto L_0x00bf
            if (r5 == r7) goto L_0x00bc
            if (r5 == r11) goto L_0x00b9
            if (r5 != r9) goto L_0x00b1
            r5 = 2817(0xb01, float:3.947E-42)
            goto L_0x00c4
        L_0x00b1:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r2 = "Invalid SEF name"
            r0.<init>((java.lang.String) r2)
            throw r0
        L_0x00b9:
            r5 = 2820(0xb04, float:3.952E-42)
            goto L_0x00c4
        L_0x00bc:
            r5 = 2816(0xb00, float:3.946E-42)
            goto L_0x00c4
        L_0x00bf:
            r5 = 2819(0xb03, float:3.95E-42)
            goto L_0x00c4
        L_0x00c2:
            r5 = 2192(0x890, float:3.072E-42)
        L_0x00c4:
            int r10 = r10 + 8
            int r4 = r4.b
            int r4 = r4 - r10
            if (r5 == r13) goto L_0x00df
            if (r5 == r14) goto L_0x00dc
            if (r5 == r15) goto L_0x00dc
            if (r5 == r8) goto L_0x00dc
            r4 = 2820(0xb04, float:3.952E-42)
            if (r5 != r4) goto L_0x00d6
            goto L_0x00dc
        L_0x00d6:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x00dc:
            r9 = r32
            goto L_0x014d
        L_0x00df:
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.lang.String r4 = r3.readString(r4)
            com.google.common.base.Splitter r5 = e
            java.util.List r4 = r5.splitToList(r4)
            r10 = 0
        L_0x00ef:
            int r5 = r4.size()
            if (r10 >= r5) goto L_0x0143
            java.lang.Object r5 = r4.get(r10)
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5
            com.google.common.base.Splitter r12 = d
            java.util.List r12 = r12.splitToList(r5)
            int r5 = r12.size()
            if (r5 != r11) goto L_0x013d
            r5 = 0
            java.lang.Object r22 = r12.get(r5)     // Catch:{ NumberFormatException -> 0x0136 }
            java.lang.String r22 = (java.lang.String) r22     // Catch:{ NumberFormatException -> 0x0136 }
            long r24 = java.lang.Long.parseLong(r22)     // Catch:{ NumberFormatException -> 0x0136 }
            java.lang.Object r22 = r12.get(r6)     // Catch:{ NumberFormatException -> 0x0136 }
            java.lang.String r22 = (java.lang.String) r22     // Catch:{ NumberFormatException -> 0x0136 }
            long r26 = java.lang.Long.parseLong(r22)     // Catch:{ NumberFormatException -> 0x0136 }
            java.lang.Object r12 = r12.get(r7)     // Catch:{ NumberFormatException -> 0x0136 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ NumberFormatException -> 0x0136 }
            int r12 = java.lang.Integer.parseInt(r12)     // Catch:{ NumberFormatException -> 0x0136 }
            int r12 = r12 - r6
            int r28 = r6 << r12
            com.google.android.exoplayer2.metadata.mp4.SlowMotionData$Segment r12 = new com.google.android.exoplayer2.metadata.mp4.SlowMotionData$Segment     // Catch:{ NumberFormatException -> 0x0136 }
            r23 = r12
            r23.<init>(r24, r26, r28)     // Catch:{ NumberFormatException -> 0x0136 }
            r9.add(r12)     // Catch:{ NumberFormatException -> 0x0136 }
            int r10 = r10 + 1
            goto L_0x00ef
        L_0x0136:
            r0 = move-exception
            com.google.android.exoplayer2.ParserException r2 = new com.google.android.exoplayer2.ParserException
            r2.<init>((java.lang.Throwable) r0)
            throw r2
        L_0x013d:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            r0.<init>()
            throw r0
        L_0x0143:
            com.google.android.exoplayer2.metadata.mp4.SlowMotionData r4 = new com.google.android.exoplayer2.metadata.mp4.SlowMotionData
            r4.<init>(r9)
            r9 = r32
            r9.add(r4)
        L_0x014d:
            int r0 = r0 + 1
            r10 = r18
            r9 = 0
            goto L_0x003f
        L_0x0154:
            r5 = 0
            r2.a = r5
            goto L_0x020b
        L_0x015a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x0160:
            r18 = r10
            long r22 = r30.getLength()
            int r3 = r1.c
            int r3 = r3 + -12
            r5 = 8
            int r3 = r3 - r5
            com.google.android.exoplayer2.util.ParsableByteArray r6 = new com.google.android.exoplayer2.util.ParsableByteArray
            r6.<init>((int) r3)
            byte[] r5 = r6.getData()
            r9 = 0
            r0.readFully(r5, r9, r3)
            r0 = 0
        L_0x017b:
            int r9 = r3 / 12
            if (r0 >= r9) goto L_0x01be
            r6.skipBytes(r7)
            short r9 = r6.readLittleEndianShort()
            if (r9 == r13) goto L_0x019a
            if (r9 == r14) goto L_0x019a
            if (r9 == r15) goto L_0x019a
            if (r9 == r8) goto L_0x019a
            r10 = 2820(0xb04, float:3.952E-42)
            if (r9 == r10) goto L_0x019c
            r12 = 8
            r6.skipBytes(r12)
            r4 = r18
            goto L_0x01b5
        L_0x019a:
            r10 = 2820(0xb04, float:3.952E-42)
        L_0x019c:
            int r12 = r1.c
            long r4 = (long) r12
            long r4 = r22 - r4
            int r12 = r6.readLittleEndianInt()
            long r13 = (long) r12
            long r4 = r4 - r13
            int r12 = r6.readLittleEndianInt()
            pa$a r13 = new pa$a
            r13.<init>(r9, r4, r12)
            r4 = r18
            r4.add(r13)
        L_0x01b5:
            int r0 = r0 + 1
            r18 = r4
            r13 = 2192(0x890, float:3.072E-42)
            r14 = 2816(0xb00, float:3.946E-42)
            goto L_0x017b
        L_0x01be:
            r4 = r18
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L_0x01cb
            r5 = 0
            r2.a = r5
            goto L_0x020b
        L_0x01cb:
            r1.b = r11
            r3 = 0
            java.lang.Object r0 = r4.get(r3)
            pa$a r0 = (defpackage.pa.a) r0
            long r3 = r0.a
            r2.a = r3
            goto L_0x020b
        L_0x01d9:
            r3 = 0
            com.google.android.exoplayer2.util.ParsableByteArray r4 = new com.google.android.exoplayer2.util.ParsableByteArray
            r5 = 8
            r4.<init>((int) r5)
            byte[] r6 = r4.getData()
            r0.readFully(r6, r3, r5)
            int r3 = r4.readLittleEndianInt()
            int r3 = r3 + r5
            r1.c = r3
            int r3 = r4.readInt()
            r4 = 1397048916(0x53454654, float:8.4728847E11)
            if (r3 == r4) goto L_0x01fd
            r3 = 0
            r2.a = r3
            goto L_0x020b
        L_0x01fd:
            long r3 = r30.getPosition()
            int r0 = r1.c
            int r0 = r0 + -12
            long r5 = (long) r0
            long r3 = r3 - r5
            r2.a = r3
            r1.b = r7
        L_0x020b:
            r0 = 1
            goto L_0x0229
        L_0x020d:
            r3 = 0
            long r5 = r30.getLength()
            r7 = -1
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x0223
            r7 = 8
            int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x0220
            goto L_0x0223
        L_0x0220:
            long r4 = r5 - r7
            goto L_0x0224
        L_0x0223:
            r4 = r3
        L_0x0224:
            r2.a = r4
            r0 = 1
            r1.b = r0
        L_0x0229:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pa.read(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.PositionHolder, java.util.List):int");
    }

    public void reset() {
        this.a.clear();
        this.b = 0;
    }
}
