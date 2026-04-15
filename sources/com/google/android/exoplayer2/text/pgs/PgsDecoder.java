package com.google.android.exoplayer2.text.pgs;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;
import java.util.zip.Inflater;

public final class PgsDecoder extends SimpleSubtitleDecoder {
    public final ParsableByteArray o = new ParsableByteArray();
    public final ParsableByteArray p = new ParsableByteArray();
    public final a q = new a();
    @Nullable
    public Inflater r;

    public static final class a {
        public final ParsableByteArray a = new ParsableByteArray();
        public final int[] b = new int[256];
        public boolean c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public int i;

        @Nullable
        public Cue build() {
            int i2;
            int i3;
            int i4;
            if (this.d == 0 || this.e == 0 || this.h == 0 || this.i == 0) {
                return null;
            }
            ParsableByteArray parsableByteArray = this.a;
            if (parsableByteArray.limit() == 0 || parsableByteArray.getPosition() != parsableByteArray.limit() || !this.c) {
                return null;
            }
            parsableByteArray.setPosition(0);
            int i5 = this.h * this.i;
            int[] iArr = new int[i5];
            int i6 = 0;
            while (i6 < i5) {
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                int[] iArr2 = this.b;
                if (readUnsignedByte != 0) {
                    i4 = i6 + 1;
                    iArr[i6] = iArr2[readUnsignedByte];
                } else {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte2 != 0) {
                        if ((readUnsignedByte2 & 64) == 0) {
                            i2 = readUnsignedByte2 & 63;
                        } else {
                            i2 = ((readUnsignedByte2 & 63) << 8) | parsableByteArray.readUnsignedByte();
                        }
                        if ((readUnsignedByte2 & 128) == 0) {
                            i3 = 0;
                        } else {
                            i3 = iArr2[parsableByteArray.readUnsignedByte()];
                        }
                        i4 = i2 + i6;
                        Arrays.fill(iArr, i6, i4, i3);
                    }
                }
                i6 = i4;
            }
            return new Cue.Builder().setBitmap(Bitmap.createBitmap(iArr, this.h, this.i, Bitmap.Config.ARGB_8888)).setPosition(((float) this.f) / ((float) this.d)).setPositionAnchor(0).setLine(((float) this.g) / ((float) this.e), 0).setLineAnchor(0).setSize(((float) this.h) / ((float) this.d)).setBitmapHeight(((float) this.i) / ((float) this.e)).build();
        }

        public void reset() {
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.i = 0;
            this.a.reset(0);
            this.c = false;
        }
    }

    public PgsDecoder() {
        super("PgsDecoder");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016a, code lost:
        r0 = r14;
        r9 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.exoplayer2.text.Subtitle e(byte[] r23, int r24, boolean r25) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            r22 = this;
            r0 = r22
            com.google.android.exoplayer2.util.ParsableByteArray r1 = r0.o
            r2 = r23
            r3 = r24
            r1.reset(r2, r3)
            int r2 = r1.bytesLeft()
            if (r2 <= 0) goto L_0x0039
            int r2 = r1.peekUnsignedByte()
            r3 = 120(0x78, float:1.68E-43)
            if (r2 != r3) goto L_0x0039
            java.util.zip.Inflater r2 = r0.r
            if (r2 != 0) goto L_0x0024
            java.util.zip.Inflater r2 = new java.util.zip.Inflater
            r2.<init>()
            r0.r = r2
        L_0x0024:
            java.util.zip.Inflater r2 = r0.r
            com.google.android.exoplayer2.util.ParsableByteArray r3 = r0.p
            boolean r2 = com.google.android.exoplayer2.util.Util.inflate(r1, r3, r2)
            if (r2 == 0) goto L_0x0039
            byte[] r2 = r3.getData()
            int r3 = r3.limit()
            r1.reset(r2, r3)
        L_0x0039:
            com.google.android.exoplayer2.text.pgs.PgsDecoder$a r2 = r0.q
            r2.reset()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
        L_0x0043:
            int r4 = r1.bytesLeft()
            r5 = 3
            if (r4 < r5) goto L_0x0184
            int r4 = r1.limit()
            int r6 = r1.readUnsignedByte()
            int r7 = r1.readUnsignedShort()
            int r8 = r1.getPosition()
            int r8 = r8 + r7
            if (r8 <= r4) goto L_0x0064
            r1.setPosition(r4)
            r0 = r1
            r9 = 0
            goto L_0x017a
        L_0x0064:
            r4 = 128(0x80, float:1.794E-43)
            if (r6 == r4) goto L_0x016d
            r11 = 0
            switch(r6) {
                case 20: goto L_0x00ea;
                case 21: goto L_0x0095;
                case 22: goto L_0x006f;
                default: goto L_0x006c;
            }
        L_0x006c:
            r14 = r1
            goto L_0x016a
        L_0x006f:
            r2.getClass()
            r4 = 19
            if (r7 >= r4) goto L_0x0077
            goto L_0x006c
        L_0x0077:
            int r4 = r1.readUnsignedShort()
            r2.d = r4
            int r4 = r1.readUnsignedShort()
            r2.e = r4
            r4 = 11
            r1.skipBytes(r4)
            int r4 = r1.readUnsignedShort()
            r2.f = r4
            int r4 = r1.readUnsignedShort()
            r2.g = r4
            goto L_0x006c
        L_0x0095:
            r2.getClass()
            r6 = 4
            if (r7 >= r6) goto L_0x009c
            goto L_0x006c
        L_0x009c:
            r1.skipBytes(r5)
            int r5 = r1.readUnsignedByte()
            r4 = r4 & r5
            if (r4 == 0) goto L_0x00a8
            r10 = 1
            goto L_0x00a9
        L_0x00a8:
            r10 = 0
        L_0x00a9:
            int r7 = r7 + -4
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r2.a
            if (r10 == 0) goto L_0x00cd
            r5 = 7
            if (r7 >= r5) goto L_0x00b3
            goto L_0x006c
        L_0x00b3:
            int r5 = r1.readUnsignedInt24()
            if (r5 >= r6) goto L_0x00ba
            goto L_0x006c
        L_0x00ba:
            int r6 = r1.readUnsignedShort()
            r2.h = r6
            int r6 = r1.readUnsignedShort()
            r2.i = r6
            int r5 = r5 + -4
            r4.reset((int) r5)
            int r7 = r7 + -7
        L_0x00cd:
            int r5 = r4.getPosition()
            int r6 = r4.limit()
            if (r5 >= r6) goto L_0x006c
            if (r7 <= 0) goto L_0x006c
            int r6 = r6 - r5
            int r6 = java.lang.Math.min(r7, r6)
            byte[] r7 = r4.getData()
            r1.readBytes(r7, r5, r6)
            int r5 = r5 + r6
            r4.setPosition(r5)
            goto L_0x006c
        L_0x00ea:
            r2.getClass()
            int r4 = r7 % 5
            r5 = 2
            if (r4 == r5) goto L_0x00f4
            goto L_0x006c
        L_0x00f4:
            r1.skipBytes(r5)
            int[] r4 = r2.b
            java.util.Arrays.fill(r4, r11)
            int r7 = r7 / 5
            r5 = 0
        L_0x00ff:
            if (r5 >= r7) goto L_0x0166
            int r6 = r1.readUnsignedByte()
            int r12 = r1.readUnsignedByte()
            int r13 = r1.readUnsignedByte()
            int r14 = r1.readUnsignedByte()
            int r15 = r1.readUnsignedByte()
            double r9 = (double) r12
            int r13 = r13 + -128
            double r12 = (double) r13
            r16 = 4608992865850220347(0x3ff66e978d4fdf3b, double:1.402)
            double r16 = r16 * r12
            r18 = r12
            double r11 = r16 + r9
            int r11 = (int) r11
            int r14 = r14 + -128
            double r12 = (double) r14
            r16 = 4599871095020959050(0x3fd60663c74fb54a, double:0.34414)
            double r16 = r16 * r12
            double r16 = r9 - r16
            r20 = 4604607620821057148(0x3fe6da3c21187e7c, double:0.71414)
            double r18 = r18 * r20
            r14 = r1
            double r0 = r16 - r18
            int r0 = (int) r0
            r16 = 4610659197712347431(0x3ffc5a1cac083127, double:1.772)
            double r12 = r12 * r16
            double r12 = r12 + r9
            int r1 = (int) r12
            int r9 = r15 << 24
            r10 = 255(0xff, float:3.57E-43)
            r12 = 0
            int r11 = com.google.android.exoplayer2.util.Util.constrainValue((int) r11, (int) r12, (int) r10)
            int r11 = r11 << 16
            r9 = r9 | r11
            int r0 = com.google.android.exoplayer2.util.Util.constrainValue((int) r0, (int) r12, (int) r10)
            int r0 = r0 << 8
            r0 = r0 | r9
            int r1 = com.google.android.exoplayer2.util.Util.constrainValue((int) r1, (int) r12, (int) r10)
            r0 = r0 | r1
            r4[r6] = r0
            int r5 = r5 + 1
            r0 = r22
            r1 = r14
            r11 = 0
            goto L_0x00ff
        L_0x0166:
            r14 = r1
            r0 = 1
            r2.c = r0
        L_0x016a:
            r0 = r14
            r9 = 0
            goto L_0x0177
        L_0x016d:
            r14 = r1
            com.google.android.exoplayer2.text.Cue r0 = r2.build()
            r2.reset()
            r9 = r0
            r0 = r14
        L_0x0177:
            r0.setPosition(r8)
        L_0x017a:
            if (r9 == 0) goto L_0x017f
            r3.add(r9)
        L_0x017f:
            r1 = r0
            r0 = r22
            goto L_0x0043
        L_0x0184:
            r8 r0 = new r8
            java.util.List r1 = java.util.Collections.unmodifiableList(r3)
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.pgs.PgsDecoder.e(byte[], int, boolean):com.google.android.exoplayer2.text.Subtitle");
    }
}
