package j$.util;

public class Base64 {

    public static class Encoder {
        private static final char[] e = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
        private static final char[] f = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
        static final Encoder g = new Encoder();
        private final byte[] a = null;
        private final int b = -1;
        private final boolean c = true;
        private final boolean d = true;

        private Encoder() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:47:0x0132  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String encodeToString(byte[] r24) {
            /*
                r23 = this;
                r0 = r23
                r1 = r24
                int r2 = r1.length
                boolean r4 = r0.d
                if (r4 == 0) goto L_0x0010
                int r2 = r2 + 2
                int r2 = r2 / 3
                int r2 = r2 * 4
                goto L_0x001d
            L_0x0010:
                int r5 = r2 % 3
                int r2 = r2 / 3
                int r2 = r2 * 4
                if (r5 != 0) goto L_0x001a
                r5 = 0
                goto L_0x001c
            L_0x001a:
                int r5 = r5 + 1
            L_0x001c:
                int r2 = r2 + r5
            L_0x001d:
                byte[] r5 = r0.a
                int r6 = r0.b
                if (r6 <= 0) goto L_0x002a
                int r7 = r2 + -1
                int r7 = r7 / r6
                int r8 = r5.length
                int r7 = r7 * r8
                int r2 = r2 + r7
            L_0x002a:
                byte[] r7 = new byte[r2]
                int r8 = r1.length
                char[] r9 = f
                char[] r10 = e
                boolean r11 = r0.c
                if (r11 == 0) goto L_0x0037
                r12 = r9
                goto L_0x0038
            L_0x0037:
                r12 = r10
            L_0x0038:
                int r13 = r8 + 0
                int r13 = r13 / 3
                int r13 = r13 * 3
                int r14 = r13 + 0
                if (r6 <= 0) goto L_0x0049
                int r15 = r6 / 4
                int r15 = r15 * 3
                if (r13 <= r15) goto L_0x0049
                r13 = r15
            L_0x0049:
                r15 = 0
                r16 = 0
            L_0x004c:
                if (r15 >= r14) goto L_0x00df
                int r3 = r15 + r13
                int r3 = java.lang.Math.min(r3, r14)
                if (r11 == 0) goto L_0x0059
                r17 = r9
                goto L_0x005b
            L_0x0059:
                r17 = r10
            L_0x005b:
                r0 = r15
                r18 = r16
            L_0x005e:
                if (r0 >= r3) goto L_0x00b3
                int r19 = r0 + 1
                byte r0 = r1[r0]
                r0 = r0 & 255(0xff, float:3.57E-43)
                int r0 = r0 << 16
                int r20 = r19 + 1
                r21 = r9
                byte r9 = r1[r19]
                r9 = r9 & 255(0xff, float:3.57E-43)
                int r9 = r9 << 8
                r0 = r0 | r9
                int r9 = r20 + 1
                r19 = r9
                byte r9 = r1[r20]
                r9 = r9 & 255(0xff, float:3.57E-43)
                r0 = r0 | r9
                int r9 = r18 + 1
                int r20 = r0 >>> 18
                r20 = r20 & 63
                r22 = r10
                char r10 = r17[r20]
                byte r10 = (byte) r10
                r7[r18] = r10
                int r10 = r9 + 1
                int r18 = r0 >>> 12
                r18 = r18 & 63
                r20 = r11
                char r11 = r17[r18]
                byte r11 = (byte) r11
                r7[r9] = r11
                int r9 = r10 + 1
                int r11 = r0 >>> 6
                r11 = r11 & 63
                char r11 = r17[r11]
                byte r11 = (byte) r11
                r7[r10] = r11
                int r18 = r9 + 1
                r0 = r0 & 63
                char r0 = r17[r0]
                byte r0 = (byte) r0
                r7[r9] = r0
                r0 = r19
                r11 = r20
                r9 = r21
                r10 = r22
                goto L_0x005e
            L_0x00b3:
                r21 = r9
                r22 = r10
                r20 = r11
                int r0 = r3 - r15
                int r0 = r0 / 3
                int r0 = r0 * 4
                int r16 = r16 + r0
                if (r0 != r6) goto L_0x00d4
                if (r3 >= r8) goto L_0x00d4
                int r0 = r5.length
                r9 = 0
            L_0x00c7:
                if (r9 >= r0) goto L_0x00d4
                byte r10 = r5[r9]
                int r11 = r16 + 1
                r7[r16] = r10
                int r9 = r9 + 1
                r16 = r11
                goto L_0x00c7
            L_0x00d4:
                r0 = r23
                r15 = r3
                r11 = r20
                r9 = r21
                r10 = r22
                goto L_0x004c
            L_0x00df:
                if (r15 >= r8) goto L_0x012e
                int r0 = r15 + 1
                byte r3 = r1[r15]
                r3 = r3 & 255(0xff, float:3.57E-43)
                int r5 = r16 + 1
                int r6 = r3 >> 2
                char r6 = r12[r6]
                byte r6 = (byte) r6
                r7[r16] = r6
                r6 = 61
                if (r0 != r8) goto L_0x010a
                int r16 = r5 + 1
                int r0 = r3 << 4
                r0 = r0 & 63
                char r0 = r12[r0]
                byte r0 = (byte) r0
                r7[r5] = r0
                if (r4 == 0) goto L_0x012e
                int r0 = r16 + 1
                r7[r16] = r6
                int r16 = r0 + 1
                r7[r0] = r6
                goto L_0x012e
            L_0x010a:
                byte r0 = r1[r0]
                r0 = r0 & 255(0xff, float:3.57E-43)
                int r1 = r5 + 1
                int r3 = r3 << 4
                r3 = r3 & 63
                int r8 = r0 >> 4
                r3 = r3 | r8
                char r3 = r12[r3]
                byte r3 = (byte) r3
                r7[r5] = r3
                int r16 = r1 + 1
                int r0 = r0 << 2
                r0 = r0 & 63
                char r0 = r12[r0]
                byte r0 = (byte) r0
                r7[r1] = r0
                if (r4 == 0) goto L_0x012e
                int r0 = r16 + 1
                r7[r16] = r6
                goto L_0x0130
            L_0x012e:
                r0 = r16
            L_0x0130:
                if (r0 == r2) goto L_0x0136
                byte[] r7 = java.util.Arrays.copyOf(r7, r0)
            L_0x0136:
                java.lang.String r0 = new java.lang.String
                int r1 = r7.length
                r2 = 0
                r0.<init>(r7, r2, r2, r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.Base64.Encoder.encodeToString(byte[]):java.lang.String");
        }
    }

    public static Encoder getUrlEncoder() {
        return Encoder.g;
    }
}
