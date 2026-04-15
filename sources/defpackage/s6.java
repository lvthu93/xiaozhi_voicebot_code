package defpackage;

/* renamed from: s6  reason: default package */
public final class s6 implements Runnable {
    public final /* synthetic */ w6 c;

    public s6(w6 w6Var) {
        this.c = w6Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c4 A[Catch:{ Exception -> 0x00fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00df A[Catch:{ Exception -> 0x00fb }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0053 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            w6 r0 = r12.c
            r0.getClass()
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00fb }
            r2 = 640000(0x9c400, float:8.96831E-40)
            r1.<init>(r2)     // Catch:{ Exception -> 0x00fb }
            r0.j = r1     // Catch:{ Exception -> 0x00fb }
            r1 = 16000(0x3e80, float:2.2421E-41)
            r3 = 12
            r4 = 2
            int r1 = android.media.AudioRecord.getMinBufferSize(r1, r3, r4)     // Catch:{ Exception -> 0x00fb }
            int r10 = r1 * 2
            android.media.AudioRecord r1 = new android.media.AudioRecord     // Catch:{ Exception -> 0x00fb }
            r6 = 1
            r7 = 16000(0x3e80, float:2.2421E-41)
            r8 = 12
            r9 = 2
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x00fb }
            r0.c = r1     // Catch:{ Exception -> 0x00fb }
            int r1 = r1.getState()     // Catch:{ Exception -> 0x00fb }
            r3 = 1
            if (r1 == r3) goto L_0x0036
            java.lang.String r1 = "AudioRecord initialization failed"
            r0.b(r1)     // Catch:{ Exception -> 0x00fb }
            goto L_0x0114
        L_0x0036:
            r0.a()     // Catch:{ Exception -> 0x00fb }
            android.media.AudioRecord r1 = r0.c     // Catch:{ Exception -> 0x00fb }
            r1.startRecording()     // Catch:{ Exception -> 0x00fb }
            r0.o = r3     // Catch:{ Exception -> 0x00fb }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00fb }
            r0.l = r3     // Catch:{ Exception -> 0x00fb }
            w6$e r1 = defpackage.w6.e.RECORDING     // Catch:{ Exception -> 0x00fb }
            r0.d(r1)     // Catch:{ Exception -> 0x00fb }
            r1 = 3200(0xc80, float:4.484E-42)
            short[] r3 = new short[r1]     // Catch:{ Exception -> 0x00fb }
            r4 = 6400(0x1900, float:8.968E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Exception -> 0x00fb }
        L_0x0053:
            boolean r5 = r0.o     // Catch:{ Exception -> 0x00fb }
            if (r5 != 0) goto L_0x0059
            goto L_0x00e5
        L_0x0059:
            android.media.AudioRecord r5 = r0.c     // Catch:{ Exception -> 0x00fb }
            r6 = 0
            int r5 = r5.read(r3, r6, r1)     // Catch:{ Exception -> 0x00fb }
            if (r5 <= 0) goto L_0x00e3
            r7 = 0
        L_0x0063:
            if (r7 >= r5) goto L_0x007f
            short r8 = r3[r7]     // Catch:{ Exception -> 0x00fb }
            float r8 = (float) r8     // Catch:{ Exception -> 0x00fb }
            r9 = 1082130432(0x40800000, float:4.0)
            float r8 = r8 * r9
            int r8 = (int) r8     // Catch:{ Exception -> 0x00fb }
            r9 = 32767(0x7fff, float:4.5916E-41)
            int r8 = java.lang.Math.min(r9, r8)     // Catch:{ Exception -> 0x00fb }
            r9 = -32768(0xffffffffffff8000, float:NaN)
            int r8 = java.lang.Math.max(r9, r8)     // Catch:{ Exception -> 0x00fb }
            short r8 = (short) r8     // Catch:{ Exception -> 0x00fb }
            r3[r7] = r8     // Catch:{ Exception -> 0x00fb }
            int r7 = r7 + 1
            goto L_0x0063
        L_0x007f:
            if (r5 > 0) goto L_0x0082
            goto L_0x009d
        L_0x0082:
            r7 = 0
            r9 = 0
        L_0x0085:
            if (r9 >= r5) goto L_0x0090
            short r10 = r3[r9]     // Catch:{ Exception -> 0x00fb }
            int r10 = r10 * r10
            long r10 = (long) r10     // Catch:{ Exception -> 0x00fb }
            long r7 = r7 + r10
            int r9 = r9 + 1
            goto L_0x0085
        L_0x0090:
            long r9 = (long) r5     // Catch:{ Exception -> 0x00fb }
            long r7 = r7 / r9
            double r7 = (double) r7     // Catch:{ Exception -> 0x00fb }
            double r7 = java.lang.Math.sqrt(r7)     // Catch:{ Exception -> 0x00fb }
            r9 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 > 0) goto L_0x009f
        L_0x009d:
            r7 = 0
            goto L_0x00a8
        L_0x009f:
            double r7 = java.lang.Math.log10(r7)     // Catch:{ Exception -> 0x00fb }
            r9 = 4621819117588971520(0x4024000000000000, double:10.0)
            double r7 = r7 * r9
            float r7 = (float) r7     // Catch:{ Exception -> 0x00fb }
        L_0x00a8:
            java.nio.ByteBuffer r8 = java.nio.ByteBuffer.wrap(r4)     // Catch:{ Exception -> 0x00fb }
            java.nio.ByteOrder r9 = java.nio.ByteOrder.LITTLE_ENDIAN     // Catch:{ Exception -> 0x00fb }
            java.nio.ByteBuffer r8 = r8.order(r9)     // Catch:{ Exception -> 0x00fb }
            java.nio.ShortBuffer r8 = r8.asShortBuffer()     // Catch:{ Exception -> 0x00fb }
            r8.put(r3, r6, r5)     // Catch:{ Exception -> 0x00fb }
            int r5 = r5 * 2
            java.io.ByteArrayOutputStream r8 = r0.j     // Catch:{ Exception -> 0x00fb }
            int r8 = r8.size()     // Catch:{ Exception -> 0x00fb }
            int r8 = r8 + r5
            if (r8 > r2) goto L_0x00c9
            java.io.ByteArrayOutputStream r8 = r0.j     // Catch:{ Exception -> 0x00fb }
            r8.write(r4, r6, r5)     // Catch:{ Exception -> 0x00fb }
        L_0x00c9:
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00fb }
            long r10 = r0.l     // Catch:{ Exception -> 0x00fb }
            long r8 = r8 - r10
            int r5 = (int) r8     // Catch:{ Exception -> 0x00fb }
            android.os.Handler r8 = r0.q     // Catch:{ Exception -> 0x00fb }
            t6 r9 = new t6     // Catch:{ Exception -> 0x00fb }
            r9.<init>(r0, r5, r7)     // Catch:{ Exception -> 0x00fb }
            r8.post(r9)     // Catch:{ Exception -> 0x00fb }
            r7 = 10000(0x2710, float:1.4013E-41)
            if (r5 < r7) goto L_0x0053
            r0.o = r6     // Catch:{ Exception -> 0x00fb }
            goto L_0x0053
        L_0x00e3:
            if (r5 >= 0) goto L_0x0053
        L_0x00e5:
            r0.c()     // Catch:{ Exception -> 0x00fb }
            android.media.AudioRecord r1 = r0.c     // Catch:{ Exception -> 0x00fb }
            r1.stop()     // Catch:{ Exception -> 0x00fb }
            android.media.AudioRecord r1 = r0.c     // Catch:{ Exception -> 0x00fb }
            r1.release()     // Catch:{ Exception -> 0x00fb }
            r1 = 0
            r0.c = r1     // Catch:{ Exception -> 0x00fb }
            java.io.ByteArrayOutputStream r1 = r0.j     // Catch:{ Exception -> 0x00fb }
            r1.size()     // Catch:{ Exception -> 0x00fb }
            goto L_0x0114
        L_0x00fb:
            r1 = move-exception
            r1.getMessage()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Recording failed: "
            r2.<init>(r3)
            java.lang.String r1 = r1.getMessage()
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.b(r1)
        L_0x0114:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.s6.run():void");
    }
}
