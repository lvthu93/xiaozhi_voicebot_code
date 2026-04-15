package defpackage;

import com.google.protobuf.p;
import com.google.protobuf.q;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* renamed from: gd  reason: default package */
public final class gd {
    public static final b a;

    /* renamed from: gd$a */
    public static class a {
        public static void a(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) throws q {
            if (!d(b2)) {
                if ((((b2 + 112) + (b << 28)) >> 30) == 0 && !d(b3) && !d(b4)) {
                    byte b5 = ((b & 7) << 18) | ((b2 & 63) << MqttWireMessage.MESSAGE_TYPE_PINGREQ) | ((b3 & 63) << 6) | (b4 & 63);
                    cArr[i] = (char) ((b5 >>> 10) + 55232);
                    cArr[i + 1] = (char) ((b5 & 1023) + 56320);
                    return;
                }
            }
            throw q.c();
        }

        public static void b(byte b, byte b2, char[] cArr, int i) throws q {
            if (b < -62 || d(b2)) {
                throw q.c();
            }
            cArr[i] = (char) (((b & 31) << 6) | (b2 & 63));
        }

        public static void c(byte b, byte b2, byte b3, char[] cArr, int i) throws q {
            if (d(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || d(b3)))) {
                throw q.c();
            }
            cArr[i] = (char) (((b & 15) << MqttWireMessage.MESSAGE_TYPE_PINGREQ) | ((b2 & 63) << 6) | (b3 & 63));
        }

        public static boolean d(byte b) {
            return b > -65;
        }
    }

    /* renamed from: gd$b */
    public static abstract class b {
        public static String b(ByteBuffer byteBuffer, int i, int i2) throws q {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            if ((i | i2 | ((byteBuffer.limit() - i) - i2)) >= 0) {
                int i3 = i + i2;
                char[] cArr = new char[i2];
                int i4 = 0;
                while (r12 < i3) {
                    byte b = byteBuffer.get(r12);
                    if (b >= 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (!z5) {
                        break;
                    }
                    i = r12 + 1;
                    cArr[i4] = (char) b;
                    i4++;
                }
                int i5 = i4;
                while (r12 < i3) {
                    int i6 = r12 + 1;
                    byte b2 = byteBuffer.get(r12);
                    if (b2 >= 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        int i7 = i5 + 1;
                        cArr[i5] = (char) b2;
                        r12 = i6;
                        while (true) {
                            i5 = i7;
                            if (r12 >= i3) {
                                break;
                            }
                            byte b3 = byteBuffer.get(r12);
                            if (b3 >= 0) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (!z4) {
                                break;
                            }
                            r12++;
                            i7 = i5 + 1;
                            cArr[i5] = (char) b3;
                        }
                    } else {
                        if (b2 < -32) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            if (b2 < -16) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            if (z3) {
                                if (i6 < i3 - 1) {
                                    int i8 = i6 + 1;
                                    a.c(b2, byteBuffer.get(i6), byteBuffer.get(i8), cArr, i5);
                                    r12 = i8 + 1;
                                    i5++;
                                } else {
                                    throw q.c();
                                }
                            } else if (i6 < i3 - 2) {
                                int i9 = i6 + 1;
                                byte b4 = byteBuffer.get(i6);
                                int i10 = i9 + 1;
                                a.a(b2, b4, byteBuffer.get(i9), byteBuffer.get(i10), cArr, i5);
                                i5 = i5 + 1 + 1;
                                r12 = i10 + 1;
                            } else {
                                throw q.c();
                            }
                        } else if (i6 < i3) {
                            a.b(b2, byteBuffer.get(i6), cArr, i5);
                            r12 = i6 + 1;
                            i5++;
                        } else {
                            throw q.c();
                        }
                    }
                }
                return new String(cArr, 0, i5);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)}));
        }

        public abstract String a(byte[] bArr, int i, int i2) throws q;

        public abstract String c(ByteBuffer byteBuffer, int i, int i2) throws q;

        public abstract int d(String str, byte[] bArr, int i, int i2);

        public abstract int e(int i, int i2, int i3, byte[] bArr);
    }

    /* renamed from: gd$c */
    public static final class c extends b {
        public final String a(byte[] bArr, int i, int i2) throws q {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            if ((i | i2 | ((bArr.length - i) - i2)) >= 0) {
                int i3 = i + i2;
                char[] cArr = new char[i2];
                int i4 = 0;
                while (r13 < i3) {
                    byte b = bArr[r13];
                    if (b >= 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (!z5) {
                        break;
                    }
                    i = r13 + 1;
                    cArr[i4] = (char) b;
                    i4++;
                }
                int i5 = i4;
                while (r13 < i3) {
                    int i6 = r13 + 1;
                    byte b2 = bArr[r13];
                    if (b2 >= 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        int i7 = i5 + 1;
                        cArr[i5] = (char) b2;
                        r13 = i6;
                        while (true) {
                            i5 = i7;
                            if (r13 >= i3) {
                                break;
                            }
                            byte b3 = bArr[r13];
                            if (b3 >= 0) {
                                z4 = true;
                            } else {
                                z4 = false;
                            }
                            if (!z4) {
                                break;
                            }
                            r13++;
                            i7 = i5 + 1;
                            cArr[i5] = (char) b3;
                        }
                    } else {
                        if (b2 < -32) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            if (b2 < -16) {
                                z3 = true;
                            } else {
                                z3 = false;
                            }
                            if (z3) {
                                if (i6 < i3 - 1) {
                                    int i8 = i6 + 1;
                                    a.c(b2, bArr[i6], bArr[i8], cArr, i5);
                                    r13 = i8 + 1;
                                    i5++;
                                } else {
                                    throw q.c();
                                }
                            } else if (i6 < i3 - 2) {
                                int i9 = i6 + 1;
                                byte b4 = bArr[i6];
                                int i10 = i9 + 1;
                                a.a(b2, b4, bArr[i9], bArr[i10], cArr, i5);
                                i5 = i5 + 1 + 1;
                                r13 = i10 + 1;
                            } else {
                                throw q.c();
                            }
                        } else if (i6 < i3) {
                            a.b(b2, bArr[i6], cArr, i5);
                            r13 = i6 + 1;
                            i5++;
                        } else {
                            throw q.c();
                        }
                    }
                }
                return new String(cArr, 0, i5);
            }
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", new Object[]{Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)}));
        }

        public final String c(ByteBuffer byteBuffer, int i, int i2) throws q {
            return b.b(byteBuffer, i, i2);
        }

        public final int d(String str, byte[] bArr, int i, int i2) {
            int i3;
            int i4;
            int i5;
            char charAt;
            int length = str.length();
            int i6 = i2 + i;
            int i7 = 0;
            while (i7 < length && (i5 = i7 + i) < i6 && (charAt = str.charAt(i7)) < 128) {
                bArr[i5] = (byte) charAt;
                i7++;
            }
            if (i7 == length) {
                return i + length;
            }
            int i8 = i + i7;
            while (i7 < length) {
                char charAt2 = str.charAt(i7);
                if (charAt2 < 128 && i8 < i6) {
                    i3 = i8 + 1;
                    bArr[i8] = (byte) charAt2;
                } else if (charAt2 < 2048 && i8 <= i6 - 2) {
                    int i9 = i8 + 1;
                    bArr[i8] = (byte) ((charAt2 >>> 6) | 960);
                    i8 = i9 + 1;
                    bArr[i9] = (byte) ((charAt2 & '?') | 128);
                    i7++;
                } else if ((charAt2 < 55296 || 57343 < charAt2) && i8 <= i6 - 3) {
                    int i10 = i8 + 1;
                    bArr[i8] = (byte) ((charAt2 >>> 12) | 480);
                    int i11 = i10 + 1;
                    bArr[i10] = (byte) (((charAt2 >>> 6) & 63) | 128);
                    i3 = i11 + 1;
                    bArr[i11] = (byte) ((charAt2 & '?') | 128);
                } else if (i8 <= i6 - 4) {
                    int i12 = i7 + 1;
                    if (i12 != str.length()) {
                        char charAt3 = str.charAt(i12);
                        if (Character.isSurrogatePair(charAt2, charAt3)) {
                            int codePoint = Character.toCodePoint(charAt2, charAt3);
                            int i13 = i8 + 1;
                            bArr[i8] = (byte) ((codePoint >>> 18) | 240);
                            int i14 = i13 + 1;
                            bArr[i13] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int i15 = i14 + 1;
                            bArr[i14] = (byte) (((codePoint >>> 6) & 63) | 128);
                            i8 = i15 + 1;
                            bArr[i15] = (byte) ((codePoint & 63) | 128);
                            i7 = i12;
                            i7++;
                        } else {
                            i7 = i12;
                        }
                    }
                    throw new d(i7 - 1, length);
                } else if (55296 > charAt2 || charAt2 > 57343 || ((i4 = i7 + 1) != str.length() && Character.isSurrogatePair(charAt2, str.charAt(i4)))) {
                    throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
                } else {
                    throw new d(i7, length);
                }
                i8 = i3;
                i7++;
            }
            return i8;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
            if (r15[r13] > -65) goto L_0x0049;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0082, code lost:
            if (r15[r13] > -65) goto L_0x0084;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
            if (r15[r13] > -65) goto L_0x0022;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int e(int r12, int r13, int r14, byte[] r15) {
            /*
                r11 = this;
                r0 = -19
                r1 = -62
                r2 = -16
                r3 = 0
                r4 = -96
                r5 = -32
                r6 = -1
                r7 = -65
                if (r12 == 0) goto L_0x0085
                if (r13 < r14) goto L_0x0013
                return r12
            L_0x0013:
                byte r8 = (byte) r12
                if (r8 >= r5) goto L_0x0023
                if (r8 < r1) goto L_0x0022
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r13 <= r7) goto L_0x001f
                goto L_0x0022
            L_0x001f:
                r13 = r12
                goto L_0x0085
            L_0x0022:
                return r6
            L_0x0023:
                if (r8 >= r2) goto L_0x004a
                int r12 = r12 >> 8
                int r12 = ~r12
                byte r12 = (byte) r12
                if (r12 != 0) goto L_0x0039
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r12 < r14) goto L_0x0036
                int r12 = defpackage.gd.d(r8, r13)
                return r12
            L_0x0036:
                r10 = r13
                r13 = r12
                r12 = r10
            L_0x0039:
                if (r12 > r7) goto L_0x0049
                if (r8 != r5) goto L_0x003f
                if (r12 < r4) goto L_0x0049
            L_0x003f:
                if (r8 != r0) goto L_0x0043
                if (r12 >= r4) goto L_0x0049
            L_0x0043:
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r13 <= r7) goto L_0x001f
            L_0x0049:
                return r6
            L_0x004a:
                int r9 = r12 >> 8
                int r9 = ~r9
                byte r9 = (byte) r9
                if (r9 != 0) goto L_0x005e
                int r12 = r13 + 1
                byte r9 = r15[r13]
                if (r12 < r14) goto L_0x005b
                int r12 = defpackage.gd.d(r8, r9)
                return r12
            L_0x005b:
                r13 = r12
                r12 = 0
                goto L_0x0061
            L_0x005e:
                int r12 = r12 >> 16
                byte r12 = (byte) r12
            L_0x0061:
                if (r12 != 0) goto L_0x0071
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r12 < r14) goto L_0x006e
                int r12 = defpackage.gd.e(r8, r9, r13)
                return r12
            L_0x006e:
                r10 = r13
                r13 = r12
                r12 = r10
            L_0x0071:
                if (r9 > r7) goto L_0x0084
                int r8 = r8 << 28
                int r9 = r9 + 112
                int r9 = r9 + r8
                int r8 = r9 >> 30
                if (r8 != 0) goto L_0x0084
                if (r12 > r7) goto L_0x0084
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r13 <= r7) goto L_0x001f
            L_0x0084:
                return r6
            L_0x0085:
                if (r13 >= r14) goto L_0x008e
                byte r12 = r15[r13]
                if (r12 < 0) goto L_0x008e
                int r13 = r13 + 1
                goto L_0x0085
            L_0x008e:
                if (r13 < r14) goto L_0x0092
                goto L_0x00ef
            L_0x0092:
                if (r13 < r14) goto L_0x0095
                goto L_0x00ef
            L_0x0095:
                int r12 = r13 + 1
                byte r13 = r15[r13]
                if (r13 >= 0) goto L_0x00f0
                if (r13 >= r5) goto L_0x00aa
                if (r12 < r14) goto L_0x00a1
                r3 = r13
                goto L_0x00ef
            L_0x00a1:
                if (r13 < r1) goto L_0x00ee
                int r13 = r12 + 1
                byte r12 = r15[r12]
                if (r12 <= r7) goto L_0x0092
                goto L_0x00ee
            L_0x00aa:
                if (r13 >= r2) goto L_0x00ca
                int r8 = r14 + -1
                if (r12 < r8) goto L_0x00b5
                int r3 = defpackage.gd.a(r15, r12, r14)
                goto L_0x00ef
            L_0x00b5:
                int r8 = r12 + 1
                byte r12 = r15[r12]
                if (r12 > r7) goto L_0x00ee
                if (r13 != r5) goto L_0x00bf
                if (r12 < r4) goto L_0x00ee
            L_0x00bf:
                if (r13 != r0) goto L_0x00c3
                if (r12 >= r4) goto L_0x00ee
            L_0x00c3:
                int r13 = r8 + 1
                byte r12 = r15[r8]
                if (r12 <= r7) goto L_0x0092
                goto L_0x00ee
            L_0x00ca:
                int r8 = r14 + -2
                if (r12 < r8) goto L_0x00d3
                int r3 = defpackage.gd.a(r15, r12, r14)
                goto L_0x00ef
            L_0x00d3:
                int r8 = r12 + 1
                byte r12 = r15[r12]
                if (r12 > r7) goto L_0x00ee
                int r13 = r13 << 28
                int r12 = r12 + 112
                int r12 = r12 + r13
                int r12 = r12 >> 30
                if (r12 != 0) goto L_0x00ee
                int r12 = r8 + 1
                byte r13 = r15[r8]
                if (r13 > r7) goto L_0x00ee
                int r13 = r12 + 1
                byte r12 = r15[r12]
                if (r12 <= r7) goto L_0x0092
            L_0x00ee:
                r3 = -1
            L_0x00ef:
                return r3
            L_0x00f0:
                r13 = r12
                goto L_0x0092
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.gd.c.e(int, int, int, byte[]):int");
        }
    }

    /* renamed from: gd$d */
    public static class d extends IllegalArgumentException {
        public d(int i, int i2) {
            super("Unpaired surrogate at index " + i + " of " + i2);
        }
    }

    /* renamed from: gd$e */
    public static final class e extends b {
        public static int f(byte[] bArr, int i, long j, int i2) {
            if (i2 == 0) {
                b bVar = gd.a;
                if (i > -12) {
                    return -1;
                }
                return i;
            } else if (i2 == 1) {
                return gd.d(i, fd.i(j, bArr));
            } else {
                if (i2 == 2) {
                    return gd.e(i, fd.i(j, bArr), fd.i(j + 1, bArr));
                }
                throw new AssertionError();
            }
        }

        public final String a(byte[] bArr, int i, int i2) throws q {
            Charset charset = p.a;
            String str = new String(bArr, i, i2, charset);
            if (str.indexOf(65533) < 0 || Arrays.equals(str.getBytes(charset), Arrays.copyOfRange(bArr, i, i2 + i))) {
                return str;
            }
            throw q.c();
        }

        public final String c(ByteBuffer byteBuffer, int i, int i2) throws q {
            long j;
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            int i3 = i;
            int i4 = i2;
            if ((i3 | i4 | ((byteBuffer.limit() - i3) - i4)) >= 0) {
                long j2 = fd.c.j(fd.g, byteBuffer) + ((long) i3);
                long j3 = ((long) i4) + j2;
                char[] cArr = new char[i4];
                int i5 = 0;
                while (j2 < j3) {
                    byte h = fd.h(j2);
                    if (h >= 0) {
                        z5 = true;
                    } else {
                        z5 = false;
                    }
                    if (!z5) {
                        break;
                    }
                    j2++;
                    cArr[i5] = (char) h;
                    i5++;
                }
                while (true) {
                    int i6 = i5;
                    while (j < j3) {
                        long j4 = j + 1;
                        byte h2 = fd.h(j);
                        if (h2 >= 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            cArr[i6] = (char) h2;
                            i6++;
                            j = j4;
                            while (j < j3) {
                                byte h3 = fd.h(j);
                                if (h3 >= 0) {
                                    z4 = true;
                                } else {
                                    z4 = false;
                                }
                                if (!z4) {
                                    break;
                                }
                                j++;
                                cArr[i6] = (char) h3;
                                i6++;
                            }
                        } else {
                            if (h2 < -32) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (!z2) {
                                if (h2 < -16) {
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                                if (z3) {
                                    if (j4 < j3 - 1) {
                                        long j5 = j4 + 1;
                                        a.c(h2, fd.h(j4), fd.h(j5), cArr, i6);
                                        i6++;
                                        j = j5 + 1;
                                    } else {
                                        throw q.c();
                                    }
                                } else if (j4 < j3 - 2) {
                                    long j6 = j4 + 1;
                                    byte h4 = fd.h(j4);
                                    long j7 = j6 + 1;
                                    byte h5 = fd.h(j6);
                                    j2 = j7 + 1;
                                    a.a(h2, h4, h5, fd.h(j7), cArr, i6);
                                    i5 = i6 + 1 + 1;
                                } else {
                                    throw q.c();
                                }
                            } else if (j4 < j3) {
                                j = j4 + 1;
                                a.b(h2, fd.h(j4), cArr, i6);
                                i6++;
                            } else {
                                throw q.c();
                            }
                        }
                    }
                    return new String(cArr, 0, i6);
                }
            }
            ByteBuffer byteBuffer2 = byteBuffer;
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", new Object[]{Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)}));
        }

        public final int d(String str, byte[] bArr, int i, int i2) {
            long j;
            long j2;
            int i3;
            char charAt;
            String str2 = str;
            byte[] bArr2 = bArr;
            int i4 = i;
            int i5 = i2;
            long j3 = (long) i4;
            long j4 = ((long) i5) + j3;
            int length = str.length();
            if (length > i5 || bArr2.length - i5 < i4) {
                throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
            }
            int i6 = 0;
            while (i6 < length && (charAt = str2.charAt(i6)) < 128) {
                fd.s(bArr2, j, (byte) charAt);
                i6++;
                j3 = 1 + j;
            }
            if (i6 == length) {
                return (int) j;
            }
            while (i6 < length) {
                char charAt2 = str2.charAt(i6);
                if (charAt2 < 128 && j < j4) {
                    j2 = j + 1;
                    fd.s(bArr2, j, (byte) charAt2);
                } else if (charAt2 < 2048 && j <= j4 - 2) {
                    long j5 = j + 1;
                    fd.s(bArr2, j, (byte) ((charAt2 >>> 6) | 960));
                    j = j5 + 1;
                    fd.s(bArr2, j5, (byte) ((charAt2 & '?') | 128));
                    i6++;
                } else if ((charAt2 < 55296 || 57343 < charAt2) && j <= j4 - 3) {
                    long j6 = j + 1;
                    fd.s(bArr2, j, (byte) ((charAt2 >>> 12) | 480));
                    long j7 = j6 + 1;
                    fd.s(bArr2, j6, (byte) (((charAt2 >>> 6) & 63) | 128));
                    j2 = j7 + 1;
                    fd.s(bArr2, j7, (byte) ((charAt2 & '?') | 128));
                } else if (j <= j4 - 4) {
                    int i7 = i6 + 1;
                    if (i7 != length) {
                        char charAt3 = str2.charAt(i7);
                        if (Character.isSurrogatePair(charAt2, charAt3)) {
                            int codePoint = Character.toCodePoint(charAt2, charAt3);
                            long j8 = j + 1;
                            fd.s(bArr2, j, (byte) ((codePoint >>> 18) | 240));
                            long j9 = j8 + 1;
                            fd.s(bArr2, j8, (byte) (((codePoint >>> 12) & 63) | 128));
                            long j10 = j9 + 1;
                            fd.s(bArr2, j9, (byte) (((codePoint >>> 6) & 63) | 128));
                            j = j10 + 1;
                            fd.s(bArr2, j10, (byte) ((codePoint & 63) | 128));
                            i6 = i7;
                            i6++;
                        } else {
                            i6 = i7;
                        }
                    }
                    throw new d(i6 - 1, length);
                } else if (55296 > charAt2 || charAt2 > 57343 || ((i3 = i6 + 1) != length && Character.isSurrogatePair(charAt2, str2.charAt(i3)))) {
                    throw new ArrayIndexOutOfBoundsException("Not enough space in output buffer to encode UTF-8 string");
                } else {
                    throw new d(i6, length);
                }
                j = j2;
                i6++;
            }
            return (int) j;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
            if (defpackage.fd.i(r8, r3) > -65) goto L_0x003c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
            if (defpackage.fd.i(r8, r3) > -65) goto L_0x0068;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int e(int r25, int r26, int r27, byte[] r28) {
            /*
                r24 = this;
                r0 = r25
                r1 = r26
                r2 = r27
                r3 = r28
                r4 = r1 | r2
                int r5 = r3.length
                int r5 = r5 - r2
                r4 = r4 | r5
                if (r4 < 0) goto L_0x018d
                long r8 = (long) r1
                long r1 = (long) r2
                r4 = -19
                r10 = -62
                r11 = -16
                r12 = 16
                r13 = -96
                r14 = -32
                r15 = -65
                r16 = -1
                r17 = 1
                if (r0 == 0) goto L_0x00b0
                int r19 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
                if (r19 < 0) goto L_0x002a
                return r0
            L_0x002a:
                byte r7 = (byte) r0
                if (r7 >= r14) goto L_0x003d
                if (r7 < r10) goto L_0x003c
                long r20 = r8 + r17
                byte r0 = defpackage.fd.i(r8, r3)
                if (r0 <= r15) goto L_0x0038
                goto L_0x003c
            L_0x0038:
                r8 = r20
                goto L_0x00b0
            L_0x003c:
                return r16
            L_0x003d:
                if (r7 >= r11) goto L_0x0069
                int r0 = r0 >> 8
                int r0 = ~r0
                byte r0 = (byte) r0
                if (r0 != 0) goto L_0x0056
                long r20 = r8 + r17
                byte r0 = defpackage.fd.i(r8, r3)
                int r8 = (r20 > r1 ? 1 : (r20 == r1 ? 0 : -1))
                if (r8 < 0) goto L_0x0054
                int r0 = defpackage.gd.d(r7, r0)
                return r0
            L_0x0054:
                r8 = r20
            L_0x0056:
                if (r0 > r15) goto L_0x0068
                if (r7 != r14) goto L_0x005c
                if (r0 < r13) goto L_0x0068
            L_0x005c:
                if (r7 != r4) goto L_0x0060
                if (r0 >= r13) goto L_0x0068
            L_0x0060:
                long r20 = r8 + r17
                byte r0 = defpackage.fd.i(r8, r3)
                if (r0 <= r15) goto L_0x0038
            L_0x0068:
                return r16
            L_0x0069:
                int r6 = r0 >> 8
                int r6 = ~r6
                byte r6 = (byte) r6
                if (r6 != 0) goto L_0x0082
                long r21 = r8 + r17
                byte r6 = defpackage.fd.i(r8, r3)
                int r0 = (r21 > r1 ? 1 : (r21 == r1 ? 0 : -1))
                if (r0 < 0) goto L_0x007e
                int r0 = defpackage.gd.d(r7, r6)
                return r0
            L_0x007e:
                r8 = r21
                r0 = 0
                goto L_0x0084
            L_0x0082:
                int r0 = r0 >> r12
                byte r0 = (byte) r0
            L_0x0084:
                if (r0 != 0) goto L_0x0097
                long r21 = r8 + r17
                byte r0 = defpackage.fd.i(r8, r3)
                int r8 = (r21 > r1 ? 1 : (r21 == r1 ? 0 : -1))
                if (r8 < 0) goto L_0x0095
                int r0 = defpackage.gd.e(r7, r6, r0)
                return r0
            L_0x0095:
                r8 = r21
            L_0x0097:
                if (r6 > r15) goto L_0x00af
                int r7 = r7 << 28
                int r6 = r6 + 112
                int r6 = r6 + r7
                int r6 = r6 >> 30
                if (r6 != 0) goto L_0x00af
                if (r0 > r15) goto L_0x00af
                long r6 = r8 + r17
                byte r0 = defpackage.fd.i(r8, r3)
                if (r0 <= r15) goto L_0x00ad
                goto L_0x00af
            L_0x00ad:
                r8 = r6
                goto L_0x00b0
            L_0x00af:
                return r16
            L_0x00b0:
                long r1 = r1 - r8
                int r0 = (int) r1
                if (r0 >= r12) goto L_0x00b6
                r2 = 0
                goto L_0x00fe
            L_0x00b6:
                int r1 = (int) r8
                r1 = r1 & 7
                int r1 = 8 - r1
                r6 = r8
                r2 = 0
            L_0x00bd:
                if (r2 >= r1) goto L_0x00cd
                long r21 = r6 + r17
                byte r6 = defpackage.fd.i(r6, r3)
                if (r6 >= 0) goto L_0x00c8
                goto L_0x00fe
            L_0x00c8:
                int r2 = r2 + 1
                r6 = r21
                goto L_0x00bd
            L_0x00cd:
                int r1 = r2 + 8
                if (r1 > r0) goto L_0x00ee
                long r21 = defpackage.fd.f
                long r4 = r21 + r6
                long r4 = defpackage.fd.o(r4, r3)
                r21 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
                long r4 = r4 & r21
                r21 = 0
                int r23 = (r4 > r21 ? 1 : (r4 == r21 ? 0 : -1))
                if (r23 == 0) goto L_0x00e7
                goto L_0x00ee
            L_0x00e7:
                r4 = 8
                long r6 = r6 + r4
                r2 = r1
                r4 = -19
                goto L_0x00cd
            L_0x00ee:
                if (r2 >= r0) goto L_0x00fd
                long r4 = r6 + r17
                byte r1 = defpackage.fd.i(r6, r3)
                if (r1 >= 0) goto L_0x00f9
                goto L_0x00fe
            L_0x00f9:
                int r2 = r2 + 1
                r6 = r4
                goto L_0x00ee
            L_0x00fd:
                r2 = r0
            L_0x00fe:
                int r0 = r0 - r2
                long r1 = (long) r2
                long r8 = r8 + r1
            L_0x0101:
                r1 = 0
            L_0x0102:
                if (r0 <= 0) goto L_0x0113
                long r1 = r8 + r17
                byte r4 = defpackage.fd.i(r8, r3)
                if (r4 < 0) goto L_0x0111
                int r0 = r0 + -1
                r8 = r1
                r1 = r4
                goto L_0x0102
            L_0x0111:
                r8 = r1
                r1 = r4
            L_0x0113:
                if (r0 != 0) goto L_0x0118
                r7 = 0
                goto L_0x018c
            L_0x0118:
                int r0 = r0 + -1
                if (r1 >= r14) goto L_0x0134
                if (r0 != 0) goto L_0x0121
                r7 = r1
                goto L_0x018c
            L_0x0121:
                int r0 = r0 + -1
                if (r1 < r10) goto L_0x0132
                long r1 = r8 + r17
                byte r4 = defpackage.fd.i(r8, r3)
                if (r4 <= r15) goto L_0x012e
                goto L_0x0132
            L_0x012e:
                r8 = r1
                r6 = -19
                goto L_0x0101
            L_0x0132:
                r7 = -1
                goto L_0x018c
            L_0x0134:
                if (r1 >= r11) goto L_0x015b
                r2 = 2
                if (r0 >= r2) goto L_0x013e
                int r7 = f(r3, r1, r8, r0)
                goto L_0x018c
            L_0x013e:
                int r0 = r0 + -2
                long r4 = r8 + r17
                byte r2 = defpackage.fd.i(r8, r3)
                if (r2 > r15) goto L_0x0132
                if (r1 != r14) goto L_0x014c
                if (r2 < r13) goto L_0x0132
            L_0x014c:
                r6 = -19
                if (r1 != r6) goto L_0x0152
                if (r2 >= r13) goto L_0x0132
            L_0x0152:
                long r8 = r4 + r17
                byte r1 = defpackage.fd.i(r4, r3)
                if (r1 <= r15) goto L_0x0101
                goto L_0x0132
            L_0x015b:
                r2 = 3
                r6 = -19
                if (r0 >= r2) goto L_0x0165
                int r7 = f(r3, r1, r8, r0)
                goto L_0x018c
            L_0x0165:
                int r0 = r0 + -3
                long r4 = r8 + r17
                byte r2 = defpackage.fd.i(r8, r3)
                if (r2 > r15) goto L_0x0132
                int r1 = r1 << 28
                int r2 = r2 + 112
                int r2 = r2 + r1
                int r1 = r2 >> 30
                if (r1 != 0) goto L_0x0132
                long r1 = r4 + r17
                byte r4 = defpackage.fd.i(r4, r3)
                if (r4 > r15) goto L_0x0132
                long r4 = r1 + r17
                byte r1 = defpackage.fd.i(r1, r3)
                if (r1 <= r15) goto L_0x0189
                goto L_0x0132
            L_0x0189:
                r8 = r4
                goto L_0x0101
            L_0x018c:
                return r7
            L_0x018d:
                java.lang.ArrayIndexOutOfBoundsException r0 = new java.lang.ArrayIndexOutOfBoundsException
                r4 = 3
                java.lang.Object[] r4 = new java.lang.Object[r4]
                int r3 = r3.length
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                r5 = 0
                r4[r5] = r3
                java.lang.Integer r1 = java.lang.Integer.valueOf(r26)
                r3 = 1
                r4[r3] = r1
                java.lang.Integer r1 = java.lang.Integer.valueOf(r27)
                r2 = 2
                r4[r2] = r1
                java.lang.String r1 = "Array length=%d, index=%d, limit=%d"
                java.lang.String r1 = java.lang.String.format(r1, r4)
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.gd.e.e(int, int, int, byte[]):int");
        }
    }

    static {
        boolean z;
        b bVar;
        if (!fd.e || !fd.d) {
            z = false;
        } else {
            z = true;
        }
        if (!z || bb.a()) {
            bVar = new c();
        } else {
            bVar = new e();
        }
        a = bVar;
    }

    public static int a(byte[] bArr, int i, int i2) {
        byte b2 = bArr[i - 1];
        int i3 = i2 - i;
        if (i3 == 0) {
            if (b2 > -12) {
                b2 = -1;
            }
            return b2;
        } else if (i3 == 1) {
            return d(b2, bArr[i]);
        } else {
            if (i3 == 2) {
                return e(b2, bArr[i], bArr[i + 1]);
            }
            throw new AssertionError();
        }
    }

    public static String b(ByteBuffer byteBuffer, int i, int i2) throws q {
        b bVar = a;
        bVar.getClass();
        if (byteBuffer.hasArray()) {
            return bVar.a(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2);
        } else if (byteBuffer.isDirect()) {
            return bVar.c(byteBuffer, i, i2);
        } else {
            return b.b(byteBuffer, i, i2);
        }
    }

    public static int c(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && str.charAt(i2) < 128) {
            i2++;
        }
        int i3 = length;
        while (true) {
            if (i2 >= length) {
                break;
            }
            char charAt = str.charAt(i2);
            if (charAt < 2048) {
                i3 += (127 - charAt) >>> 31;
                i2++;
            } else {
                int length2 = str.length();
                while (i2 < length2) {
                    char charAt2 = str.charAt(i2);
                    if (charAt2 < 2048) {
                        i += (127 - charAt2) >>> 31;
                    } else {
                        i += 2;
                        if (55296 <= charAt2 && charAt2 <= 57343) {
                            if (Character.codePointAt(str, i2) >= 65536) {
                                i2++;
                            } else {
                                throw new d(i2, length2);
                            }
                        }
                    }
                    i2++;
                }
                i3 += i;
            }
        }
        if (i3 >= length) {
            return i3;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (((long) i3) + 4294967296L));
    }

    public static int d(int i, int i2) {
        if (i > -12 || i2 > -65) {
            return -1;
        }
        return i ^ (i2 << 8);
    }

    public static int e(int i, int i2, int i3) {
        if (i > -12 || i2 > -65 || i3 > -65) {
            return -1;
        }
        return (i ^ (i2 << 8)) ^ (i3 << 16);
    }

    public static boolean f(byte[] bArr, int i, int i2) {
        return a.e(0, i, i2, bArr) == 0;
    }
}
