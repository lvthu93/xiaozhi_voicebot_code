package defpackage;

import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;

/* renamed from: ck  reason: default package */
public final class ck implements cm, cl, Cloneable, ByteChannel {
    public static final byte[] g = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    public qa c;
    public long f;

    /* renamed from: ck$a */
    public class a extends InputStream implements InputStreamRetargetInterface {
        public a() {
        }

        public final int available() {
            return (int) Math.min(ck.this.f, 2147483647L);
        }

        public final void close() {
        }

        public final int read() {
            ck ckVar = ck.this;
            if (ckVar.f > 0) {
                return ckVar.readByte() & 255;
            }
            return -1;
        }

        public final String toString() {
            return ck.this + ".inputStream()";
        }

        public final /* synthetic */ long transferTo(OutputStream outputStream) {
            return DesugarInputStream.transferTo(this, outputStream);
        }

        public final int read(byte[] bArr, int i, int i2) {
            return ck.this.read(bArr, i, i2);
        }
    }

    /* renamed from: ck$b */
    public static final class b implements Closeable {
        public ck c;
        public boolean f;
        public qa g;
        public long h = -1;
        public byte[] i;
        public int j = -1;
        public int k = -1;

        public final void close() {
            if (this.c != null) {
                this.c = null;
                this.g = null;
                this.h = -1;
                this.i = null;
                this.j = -1;
                this.k = -1;
                return;
            }
            throw new IllegalStateException("not attached to a buffer");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
            r3 = r0.c;
            r6 = r3.f;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int d(long r18) {
            /*
                r17 = this;
                r0 = r17
                r1 = r18
                r3 = -1
                int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r5 < 0) goto L_0x00ae
                ck r3 = r0.c
                long r6 = r3.f
                int r4 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
                if (r4 > 0) goto L_0x00ae
                if (r5 == 0) goto L_0x00a1
                if (r4 != 0) goto L_0x0018
                goto L_0x00a1
            L_0x0018:
                qa r3 = r3.c
                qa r4 = r0.g
                r8 = 0
                if (r4 == 0) goto L_0x0036
                long r10 = r0.h
                int r5 = r0.j
                int r12 = r4.b
                int r5 = r5 - r12
                long r12 = (long) r5
                long r10 = r10 - r12
                int r5 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
                if (r5 <= 0) goto L_0x002f
                r6 = r10
                goto L_0x0037
            L_0x002f:
                r8 = r10
                r16 = r4
                r4 = r3
                r3 = r16
                goto L_0x0037
            L_0x0036:
                r4 = r3
            L_0x0037:
                long r10 = r6 - r1
                long r12 = r1 - r8
                int r5 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
                if (r5 <= 0) goto L_0x004e
            L_0x003f:
                int r4 = r3.c
                int r5 = r3.b
                int r4 = r4 - r5
                long r4 = (long) r4
                long r4 = r4 + r8
                int r6 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
                if (r6 < 0) goto L_0x005e
                qa r3 = r3.f
                r8 = r4
                goto L_0x003f
            L_0x004e:
                int r3 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
                if (r3 <= 0) goto L_0x005c
                qa r4 = r4.g
                int r3 = r4.c
                int r5 = r4.b
                int r3 = r3 - r5
                long r8 = (long) r3
                long r6 = r6 - r8
                goto L_0x004e
            L_0x005c:
                r3 = r4
                r8 = r6
            L_0x005e:
                boolean r4 = r0.f
                if (r4 == 0) goto L_0x008c
                boolean r4 = r3.d
                if (r4 == 0) goto L_0x008c
                qa r4 = new qa
                byte[] r5 = r3.a
                java.lang.Object r5 = r5.clone()
                r11 = r5
                byte[] r11 = (byte[]) r11
                int r12 = r3.b
                int r13 = r3.c
                r14 = 0
                r15 = 1
                r10 = r4
                r10.<init>(r11, r12, r13, r14, r15)
                ck r5 = r0.c
                qa r6 = r5.c
                if (r6 != r3) goto L_0x0083
                r5.c = r4
            L_0x0083:
                r3.b(r4)
                qa r3 = r4.g
                r3.a()
                r3 = r4
            L_0x008c:
                r0.g = r3
                r0.h = r1
                byte[] r4 = r3.a
                r0.i = r4
                int r4 = r3.b
                long r1 = r1 - r8
                int r2 = (int) r1
                int r4 = r4 + r2
                r0.j = r4
                int r1 = r3.c
                r0.k = r1
                int r1 = r1 - r4
                return r1
            L_0x00a1:
                r3 = 0
                r0.g = r3
                r0.h = r1
                r0.i = r3
                r1 = -1
                r0.j = r1
                r0.k = r1
                return r1
            L_0x00ae:
                java.lang.ArrayIndexOutOfBoundsException r3 = new java.lang.ArrayIndexOutOfBoundsException
                r4 = 2
                java.lang.Object[] r4 = new java.lang.Object[r4]
                r5 = 0
                java.lang.Long r1 = java.lang.Long.valueOf(r18)
                r4[r5] = r1
                ck r1 = r0.c
                long r1 = r1.f
                java.lang.Long r1 = java.lang.Long.valueOf(r1)
                r2 = 1
                r4[r2] = r1
                java.lang.String r1 = "offset=%s > size=%s"
                java.lang.String r1 = java.lang.String.format(r1, r4)
                r3.<init>(r1)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.ck.b.d(long):int");
        }
    }

    public final ck a() {
        return this;
    }

    public final boolean aa(cq cqVar) {
        int t = cqVar.t();
        if (t < 0 || this.f - 0 < ((long) t) || cqVar.t() - 0 < t) {
            return false;
        }
        for (int i = 0; i < t; i++) {
            if (i(((long) i) + 0) != cqVar.n(0 + i)) {
                return false;
            }
        }
        return true;
    }

    public final void ab(b bVar) {
        if (bVar.c == null) {
            bVar.c = this;
            bVar.f = true;
            return;
        }
        throw new IllegalStateException("already attached to a buffer");
    }

    public final cq ac() {
        return new cq(f());
    }

    public final String ad(long j, Charset charset) throws EOFException {
        jd.a(this.f, 0, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (j > 2147483647L) {
            throw new IllegalArgumentException(y2.g("byteCount > Integer.MAX_VALUE: ", j));
        } else if (j == 0) {
            return "";
        } else {
            qa qaVar = this.c;
            int i = qaVar.b;
            if (((long) i) + j > ((long) qaVar.c)) {
                return new String(v(j), charset);
            }
            String str = new String(qaVar.a, i, (int) j, charset);
            int i2 = (int) (((long) qaVar.b) + j);
            qaVar.b = i2;
            this.f -= j;
            if (i2 == qaVar.c) {
                this.c = qaVar.a();
                sa.a(qaVar);
            }
            return str;
        }
    }

    public final String ae() {
        try {
            return ad(this.f, jd.a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final String af(long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (i(j2) == 13) {
                String ad = ad(j2, jd.a);
                skip(2);
                return ad;
            }
        }
        String ad2 = ad(j, jd.a);
        skip(1);
        return ad2;
    }

    public final qa ag(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException();
        }
        qa qaVar = this.c;
        if (qaVar == null) {
            qa b2 = sa.b();
            this.c = b2;
            b2.g = b2;
            b2.f = b2;
            return b2;
        }
        qa qaVar2 = qaVar.g;
        if (qaVar2.c + i <= 8192 && qaVar2.e) {
            return qaVar2;
        }
        qa b3 = sa.b();
        qaVar2.b(b3);
        return b3;
    }

    public final void ah(cq cqVar) {
        if (cqVar != null) {
            cqVar.y(this);
            return;
        }
        throw new IllegalArgumentException("byteString == null");
    }

    public final void ai(int i) {
        qa ag = ag(1);
        int i2 = ag.c;
        ag.c = i2 + 1;
        ag.a[i2] = (byte) i;
        this.f++;
    }

    public final ck aj(long j) {
        byte[] bArr;
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i == 0) {
            ai(48);
            return this;
        }
        int i2 = 1;
        boolean z = false;
        if (i < 0) {
            j = -j;
            if (j < 0) {
                ap(0, 20, "-9223372036854775808");
                return this;
            }
            z = true;
        }
        if (j < 100000000) {
            if (j < 10000) {
                if (j < 100) {
                    if (j >= 10) {
                        i2 = 2;
                    }
                } else if (j < 1000) {
                    i2 = 3;
                } else {
                    i2 = 4;
                }
            } else if (j < 1000000) {
                if (j < 100000) {
                    i2 = 5;
                } else {
                    i2 = 6;
                }
            } else if (j < 10000000) {
                i2 = 7;
            } else {
                i2 = 8;
            }
        } else if (j < 1000000000000L) {
            if (j < 10000000000L) {
                if (j < 1000000000) {
                    i2 = 9;
                } else {
                    i2 = 10;
                }
            } else if (j < 100000000000L) {
                i2 = 11;
            } else {
                i2 = 12;
            }
        } else if (j < 1000000000000000L) {
            if (j < 10000000000000L) {
                i2 = 13;
            } else if (j < 100000000000000L) {
                i2 = 14;
            } else {
                i2 = 15;
            }
        } else if (j < 100000000000000000L) {
            if (j < 10000000000000000L) {
                i2 = 16;
            } else {
                i2 = 17;
            }
        } else if (j < 1000000000000000000L) {
            i2 = 18;
        } else {
            i2 = 19;
        }
        if (z) {
            i2++;
        }
        qa ag = ag(i2);
        int i3 = ag.c + i2;
        while (true) {
            bArr = ag.a;
            if (j == 0) {
                break;
            }
            i3--;
            bArr[i3] = g[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i3 - 1] = 45;
        }
        ag.c += i2;
        this.f += (long) i2;
        return this;
    }

    public final ck ak(long j) {
        if (j == 0) {
            ai(48);
            return this;
        }
        int numberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        qa ag = ag(numberOfTrailingZeros);
        int i = ag.c;
        int i2 = i + numberOfTrailingZeros;
        while (true) {
            i2--;
            if (i2 >= i) {
                ag.a[i2] = g[(int) (15 & j)];
                j >>>= 4;
            } else {
                ag.c += numberOfTrailingZeros;
                this.f += (long) numberOfTrailingZeros;
                return this;
            }
        }
    }

    public final void al(int i) {
        qa ag = ag(4);
        int i2 = ag.c;
        int i3 = i2 + 1;
        byte[] bArr = ag.a;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        ag.c = i5 + 1;
        this.f += 4;
    }

    public final void am(long j) {
        qa ag = ag(8);
        int i = ag.c;
        int i2 = i + 1;
        byte[] bArr = ag.a;
        bArr[i] = (byte) ((int) ((j >>> 56) & 255));
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((int) ((j >>> 48) & 255));
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((int) ((j >>> 40) & 255));
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((int) ((j >>> 32) & 255));
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((int) ((j >>> 24) & 255));
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((int) ((j >>> 16) & 255));
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((int) ((j >>> 8) & 255));
        bArr[i8] = (byte) ((int) (j & 255));
        ag.c = i8 + 1;
        this.f += 8;
    }

    public final void an(int i) {
        qa ag = ag(2);
        int i2 = ag.c;
        int i3 = i2 + 1;
        byte[] bArr = ag.a;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i3] = (byte) (i & 255);
        ag.c = i3 + 1;
        this.f += 2;
    }

    public final ck ao(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalAccessError(y2.f("beginIndex < 0: ", i));
        } else if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        } else if (i2 > str.length()) {
            StringBuilder n = y2.n("endIndex > string.length: ", i2, " > ");
            n.append(str.length());
            throw new IllegalArgumentException(n.toString());
        } else if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        } else if (charset.equals(jd.a)) {
            ap(i, i2, str);
            return this;
        } else {
            byte[] bytes = str.substring(i, i2).getBytes(charset);
            write(bytes, 0, bytes.length);
            return this;
        }
    }

    public final void ap(int i, int i2, String str) {
        char c2;
        char charAt;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        } else if (i < 0) {
            throw new IllegalArgumentException(y2.f("beginIndex < 0: ", i));
        } else if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        } else if (i2 <= str.length()) {
            while (i < i2) {
                char charAt2 = str.charAt(i);
                if (charAt2 < 128) {
                    qa ag = ag(1);
                    int i3 = ag.c - i;
                    int min = Math.min(i2, 8192 - i3);
                    int i4 = i + 1;
                    byte[] bArr = ag.a;
                    bArr[i + i3] = (byte) charAt2;
                    while (true) {
                        i = i4;
                        if (i >= min || (charAt = str.charAt(i)) >= 128) {
                            int i5 = ag.c;
                            int i6 = (i3 + i) - i5;
                            ag.c = i5 + i6;
                            this.f += (long) i6;
                        } else {
                            i4 = i + 1;
                            bArr[i + i3] = (byte) charAt;
                        }
                    }
                    int i52 = ag.c;
                    int i62 = (i3 + i) - i52;
                    ag.c = i52 + i62;
                    this.f += (long) i62;
                } else {
                    if (charAt2 < 2048) {
                        ai((charAt2 >> 6) | 192);
                        ai((charAt2 & '?') | 128);
                    } else if (charAt2 < 55296 || charAt2 > 57343) {
                        ai((charAt2 >> 12) | 224);
                        ai(((charAt2 >> 6) & 63) | 128);
                        ai((charAt2 & '?') | 128);
                    } else {
                        int i7 = i + 1;
                        if (i7 < i2) {
                            c2 = str.charAt(i7);
                        } else {
                            c2 = 0;
                        }
                        if (charAt2 > 56319 || c2 < 56320 || c2 > 57343) {
                            ai(63);
                            i = i7;
                        } else {
                            int i8 = (((charAt2 & 10239) << 10) | (9215 & c2)) + 0;
                            ai((i8 >> 18) | 240);
                            ai(((i8 >> 12) & 63) | 128);
                            ai(((i8 >> 6) & 63) | 128);
                            ai((i8 & 63) | 128);
                            i += 2;
                        }
                    }
                    i++;
                }
            }
        } else {
            StringBuilder n = y2.n("endIndex > string.length: ", i2, " > ");
            n.append(str.length());
            throw new IllegalArgumentException(n.toString());
        }
    }

    public final void aq(int i) {
        if (i < 128) {
            ai(i);
        } else if (i < 2048) {
            ai((i >> 6) | 192);
            ai((i & 63) | 128);
        } else if (i < 65536) {
            if (i < 55296 || i > 57343) {
                ai((i >> 12) | 224);
                ai(((i >> 6) & 63) | 128);
                ai((i & 63) | 128);
                return;
            }
            ai(63);
        } else if (i <= 1114111) {
            ai((i >> 18) | 240);
            ai(((i >> 12) & 63) | 128);
            ai(((i >> 6) & 63) | 128);
            ai((i & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
        }
    }

    public final cq b(long j) throws EOFException {
        return new cq(v(j));
    }

    public final cl c() {
        return this;
    }

    public final void clear() {
        try {
            skip(this.f);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final void close() {
    }

    /* renamed from: d */
    public final ck clone() {
        ck ckVar = new ck();
        if (this.f == 0) {
            return ckVar;
        }
        qa c2 = this.c.c();
        ckVar.c = c2;
        c2.g = c2;
        c2.f = c2;
        qa qaVar = this.c;
        while (true) {
            qaVar = qaVar.f;
            if (qaVar != this.c) {
                ckVar.c.g.b(qaVar.c());
            } else {
                ckVar.f = this.f;
                return ckVar;
            }
        }
    }

    public final long e() {
        long j = this.f;
        if (j == 0) {
            return 0;
        }
        qa qaVar = this.c.g;
        int i = qaVar.c;
        if (i >= 8192 || !qaVar.e) {
            return j;
        }
        return j - ((long) (i - qaVar.b));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ck)) {
            return false;
        }
        ck ckVar = (ck) obj;
        long j = this.f;
        if (j != ckVar.f) {
            return false;
        }
        long j2 = 0;
        if (j == 0) {
            return true;
        }
        qa qaVar = this.c;
        qa qaVar2 = ckVar.c;
        int i = qaVar.b;
        int i2 = qaVar2.b;
        while (j2 < this.f) {
            long min = (long) Math.min(qaVar.c - i, qaVar2.c - i2);
            int i3 = 0;
            while (((long) i3) < min) {
                int i4 = i + 1;
                int i5 = i2 + 1;
                if (qaVar.a[i] != qaVar2.a[i2]) {
                    return false;
                }
                i3++;
                i = i4;
                i2 = i5;
            }
            if (i == qaVar.c) {
                qaVar = qaVar.f;
                i = qaVar.b;
            }
            if (i2 == qaVar2.c) {
                qaVar2 = qaVar2.f;
                i2 = qaVar2.b;
            }
            j2 += min;
        }
        return true;
    }

    public final byte[] f() {
        try {
            return v(this.f);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final void flush() {
    }

    public final boolean g() {
        return this.f == 0;
    }

    public final void h(long j, ck ckVar, long j2) {
        if (ckVar != null) {
            jd.a(this.f, j, j2);
            if (j2 != 0) {
                ckVar.f += j2;
                qa qaVar = this.c;
                while (true) {
                    long j3 = (long) (qaVar.c - qaVar.b);
                    if (j < j3) {
                        break;
                    }
                    j -= j3;
                    qaVar = qaVar.f;
                }
                while (j2 > 0) {
                    qa c2 = qaVar.c();
                    int i = (int) (((long) c2.b) + j);
                    c2.b = i;
                    c2.c = Math.min(i + ((int) j2), c2.c);
                    qa qaVar2 = ckVar.c;
                    if (qaVar2 == null) {
                        c2.g = c2;
                        c2.f = c2;
                        ckVar.c = c2;
                    } else {
                        qaVar2.g.b(c2);
                    }
                    j2 -= (long) (c2.c - c2.b);
                    qaVar = qaVar.f;
                    j = 0;
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("out == null");
    }

    public final int hashCode() {
        qa qaVar = this.c;
        if (qaVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = qaVar.c;
            for (int i3 = qaVar.b; i3 < i2; i3++) {
                i = (i * 31) + qaVar.a[i3];
            }
            qaVar = qaVar.f;
        } while (qaVar != this.c);
        return i;
    }

    public final byte i(long j) {
        int i;
        jd.a(this.f, j, 1);
        long j2 = this.f;
        if (j2 - j > j) {
            qa qaVar = this.c;
            while (true) {
                int i2 = qaVar.c;
                int i3 = qaVar.b;
                long j3 = (long) (i2 - i3);
                if (j < j3) {
                    return qaVar.a[i3 + ((int) j)];
                }
                j -= j3;
                qaVar = qaVar.f;
            }
        } else {
            long j4 = j - j2;
            qa qaVar2 = this.c;
            do {
                qaVar2 = qaVar2.g;
                int i4 = qaVar2.c;
                i = qaVar2.b;
                j4 += (long) (i4 - i);
            } while (j4 < 0);
            return qaVar2.a[i + ((int) j4)];
        }
    }

    public final InputStream inputStream() {
        return new a();
    }

    public final boolean isOpen() {
        return true;
    }

    public final long j(jb jbVar) throws IOException {
        if (jbVar != null) {
            long j = 0;
            while (true) {
                long read = jbVar.read(this, 8192);
                if (read == -1) {
                    return j;
                }
                j += read;
            }
        } else {
            throw new IllegalArgumentException("source == null");
        }
    }

    public final cl k() throws IOException {
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004a, code lost:
        if (r2 != false) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004c, code lost:
        r1.readByte();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005e, code lost:
        throw new java.lang.NumberFormatException("Number too large: ".concat(r1.ae()));
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008b A[EDGE_INSN: B:47:0x008b->B:29:0x008b ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x001b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long l() {
        /*
            r17 = this;
            r0 = r17
            long r1 = r0.f
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x00aa
            r1 = 0
            r5 = -7
            r6 = r5
            r2 = 0
            r4 = r3
            r3 = 0
        L_0x0011:
            qa r8 = r0.c
            byte[] r9 = r8.a
            int r10 = r8.b
            int r11 = r8.c
        L_0x0019:
            if (r10 >= r11) goto L_0x008b
            byte r12 = r9[r10]
            r13 = 48
            if (r12 < r13) goto L_0x005f
            r13 = 57
            if (r12 > r13) goto L_0x005f
            int r13 = 48 - r12
            r14 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r16 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r16 < 0) goto L_0x003f
            if (r16 != 0) goto L_0x0038
            long r14 = (long) r13
            int r16 = (r14 > r6 ? 1 : (r14 == r6 ? 0 : -1))
            if (r16 >= 0) goto L_0x0038
            goto L_0x003f
        L_0x0038:
            r14 = 10
            long r4 = r4 * r14
            long r12 = (long) r13
            long r4 = r4 + r12
            goto L_0x006a
        L_0x003f:
            ck r1 = new ck
            r1.<init>()
            r1.aj(r4)
            r1.ai(r12)
            if (r2 != 0) goto L_0x004f
            r1.readByte()
        L_0x004f:
            java.lang.NumberFormatException r2 = new java.lang.NumberFormatException
            java.lang.String r1 = r1.ae()
            java.lang.String r3 = "Number too large: "
            java.lang.String r1 = r3.concat(r1)
            r2.<init>(r1)
            throw r2
        L_0x005f:
            r13 = 45
            r14 = 1
            if (r12 != r13) goto L_0x006f
            if (r1 != 0) goto L_0x006f
            r12 = 1
            long r6 = r6 - r12
            r2 = 1
        L_0x006a:
            int r10 = r10 + 1
            int r1 = r1 + 1
            goto L_0x0019
        L_0x006f:
            if (r1 == 0) goto L_0x0073
            r3 = 1
            goto L_0x008b
        L_0x0073:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Expected leading [0-9] or '-' character but was 0x"
            r2.<init>(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r12)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x008b:
            if (r10 != r11) goto L_0x0097
            qa r9 = r8.a()
            r0.c = r9
            defpackage.sa.a(r8)
            goto L_0x0099
        L_0x0097:
            r8.b = r10
        L_0x0099:
            if (r3 != 0) goto L_0x009f
            qa r8 = r0.c
            if (r8 != 0) goto L_0x0011
        L_0x009f:
            long r6 = r0.f
            long r8 = (long) r1
            long r6 = r6 - r8
            r0.f = r6
            if (r2 == 0) goto L_0x00a8
            goto L_0x00a9
        L_0x00a8:
            long r4 = -r4
        L_0x00a9:
            return r4
        L_0x00aa:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "size == 0"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ck.l():long");
    }

    public final String m(long j) throws EOFException {
        if (j >= 0) {
            long j2 = Long.MAX_VALUE;
            if (j != Long.MAX_VALUE) {
                j2 = j + 1;
            }
            long n = n((byte) 10, 0, j2);
            if (n != -1) {
                return af(n);
            }
            if (j2 < this.f && i(j2 - 1) == 13 && i(j2) == 10) {
                return af(j2);
            }
            ck ckVar = new ck();
            h(0, ckVar, Math.min(32, this.f));
            throw new EOFException("\\n not found: limit=" + Math.min(this.f, j) + " content=" + ckVar.ac().o() + 8230);
        }
        throw new IllegalArgumentException(y2.g("limit < 0: ", j));
    }

    public final long n(byte b2, long j, long j2) {
        long j3;
        qa qaVar;
        long j4 = 0;
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("size=%s fromIndex=%s toIndex=%s", new Object[]{Long.valueOf(this.f), Long.valueOf(j), Long.valueOf(j2)}));
        }
        long j5 = this.f;
        if (j2 > j5) {
            j3 = j5;
        } else {
            j3 = j2;
        }
        if (j == j3 || (qaVar = this.c) == null) {
            return -1;
        }
        if (j5 - j < j) {
            while (j5 > j) {
                qaVar = qaVar.g;
                j5 -= (long) (qaVar.c - qaVar.b);
            }
        } else {
            while (true) {
                long j6 = ((long) (qaVar.c - qaVar.b)) + j4;
                if (j6 >= j) {
                    break;
                }
                qaVar = qaVar.f;
                j4 = j6;
            }
            j5 = j4;
        }
        long j7 = j;
        while (j5 < j3) {
            byte[] bArr = qaVar.a;
            int min = (int) Math.min((long) qaVar.c, (((long) qaVar.b) + j3) - j5);
            for (int i = (int) ((((long) qaVar.b) + j7) - j5); i < min; i++) {
                if (bArr[i] == b2) {
                    return ((long) (i - qaVar.b)) + j5;
                }
            }
            byte b3 = b2;
            j5 += (long) (qaVar.c - qaVar.b);
            qaVar = qaVar.f;
            j7 = j5;
        }
        return -1;
    }

    public final cl o(String str) throws IOException {
        ap(0, str.length(), str);
        return this;
    }

    public final void p(ck ckVar, long j) throws EOFException {
        long j2 = this.f;
        if (j2 >= j) {
            ckVar.write(this, j);
        } else {
            ckVar.write(this, j2);
            throw new EOFException();
        }
    }

    public final String q(Charset charset) {
        try {
            return ad(this.f, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final /* bridge */ /* synthetic */ cl r(cq cqVar) throws IOException {
        ah(cqVar);
        return this;
    }

    public final int read(byte[] bArr, int i, int i2) {
        jd.a((long) bArr.length, (long) i, (long) i2);
        qa qaVar = this.c;
        if (qaVar == null) {
            return -1;
        }
        int min = Math.min(i2, qaVar.c - qaVar.b);
        System.arraycopy(qaVar.a, qaVar.b, bArr, i, min);
        int i3 = qaVar.b + min;
        qaVar.b = i3;
        this.f -= (long) min;
        if (i3 == qaVar.c) {
            this.c = qaVar.a();
            sa.a(qaVar);
        }
        return min;
    }

    public final byte readByte() {
        long j = this.f;
        if (j != 0) {
            qa qaVar = this.c;
            int i = qaVar.b;
            int i2 = qaVar.c;
            int i3 = i + 1;
            byte b2 = qaVar.a[i];
            this.f = j - 1;
            if (i3 == i2) {
                this.c = qaVar.a();
                sa.a(qaVar);
            } else {
                qaVar.b = i3;
            }
            return b2;
        }
        throw new IllegalStateException("size == 0");
    }

    public final void readFully(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int read = read(bArr, i, bArr.length - i);
            if (read != -1) {
                i += read;
            } else {
                throw new EOFException();
            }
        }
    }

    public final int readInt() {
        long j = this.f;
        if (j >= 4) {
            qa qaVar = this.c;
            int i = qaVar.b;
            int i2 = qaVar.c;
            if (i2 - i < 4) {
                return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
            }
            int i3 = i + 1;
            byte[] bArr = qaVar.a;
            int i4 = i3 + 1;
            byte b2 = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
            int i5 = i4 + 1;
            byte b3 = b2 | ((bArr[i4] & 255) << 8);
            int i6 = i5 + 1;
            byte b4 = b3 | (bArr[i5] & 255);
            this.f = j - 4;
            if (i6 == i2) {
                this.c = qaVar.a();
                sa.a(qaVar);
            } else {
                qaVar.b = i6;
            }
            return b4;
        }
        throw new IllegalStateException("size < 4: " + this.f);
    }

    public final long readLong() {
        long j = this.f;
        if (j >= 8) {
            qa qaVar = this.c;
            int i = qaVar.b;
            int i2 = qaVar.c;
            if (i2 - i < 8) {
                return ((((long) readInt()) & 4294967295L) << 32) | (4294967295L & ((long) readInt()));
            }
            int i3 = i + 1;
            byte[] bArr = qaVar.a;
            int i4 = i3 + 1;
            long j2 = (((long) bArr[i3]) & 255) << 48;
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            long j3 = j2 | ((((long) bArr[i]) & 255) << 56) | ((((long) bArr[i4]) & 255) << 40) | ((((long) bArr[i5]) & 255) << 32);
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            long j4 = j3 | ((((long) bArr[i6]) & 255) << 24) | ((((long) bArr[i7]) & 255) << 16);
            int i9 = i8 + 1;
            int i10 = i9 + 1;
            long j5 = j4 | ((((long) bArr[i8]) & 255) << 8) | (((long) bArr[i9]) & 255);
            this.f = j - 8;
            if (i10 == i2) {
                this.c = qaVar.a();
                sa.a(qaVar);
            } else {
                qaVar.b = i10;
            }
            return j5;
        }
        throw new IllegalStateException("size < 8: " + this.f);
    }

    public final short readShort() {
        long j = this.f;
        if (j >= 2) {
            qa qaVar = this.c;
            int i = qaVar.b;
            int i2 = qaVar.c;
            if (i2 - i < 2) {
                return (short) (((readByte() & 255) << 8) | (readByte() & 255));
            }
            int i3 = i + 1;
            byte[] bArr = qaVar.a;
            int i4 = i3 + 1;
            byte b2 = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
            this.f = j - 2;
            if (i4 == i2) {
                this.c = qaVar.a();
                sa.a(qaVar);
            } else {
                qaVar.b = i4;
            }
            return (short) b2;
        }
        throw new IllegalStateException("size < 2: " + this.f);
    }

    public final /* bridge */ /* synthetic */ cl s(long j) throws IOException {
        ak(j);
        return this;
    }

    public final void skip(long j) throws EOFException {
        while (j > 0) {
            qa qaVar = this.c;
            if (qaVar != null) {
                int min = (int) Math.min(j, (long) (qaVar.c - qaVar.b));
                long j2 = (long) min;
                this.f -= j2;
                j -= j2;
                qa qaVar2 = this.c;
                int i = qaVar2.b + min;
                qaVar2.b = i;
                if (i == qaVar2.c) {
                    this.c = qaVar2.a();
                    sa.a(qaVar2);
                }
            } else {
                throw new EOFException();
            }
        }
    }

    public final boolean t(long j) {
        return this.f >= j;
    }

    public final lc timeout() {
        return lc.NONE;
    }

    public final String toString() {
        cq cqVar;
        long j = this.f;
        if (j <= 2147483647L) {
            int i = (int) j;
            if (i == 0) {
                cqVar = cq.i;
            } else {
                cqVar = new ta(this, i);
            }
            return cqVar.toString();
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.f);
    }

    public final String u() throws EOFException {
        return m(Long.MAX_VALUE);
    }

    public final byte[] v(long j) throws EOFException {
        jd.a(this.f, 0, j);
        if (j <= 2147483647L) {
            byte[] bArr = new byte[((int) j)];
            readFully(bArr);
            return bArr;
        }
        throw new IllegalArgumentException(y2.g("byteCount > Integer.MAX_VALUE: ", j));
    }

    public final void w(long j) throws EOFException {
        if (this.f < j) {
            throw new EOFException();
        }
    }

    public final /* bridge */ /* synthetic */ cl writeByte(int i) throws IOException {
        ai(i);
        return this;
    }

    public final /* bridge */ /* synthetic */ cl writeInt(int i) throws IOException {
        al(i);
        return this;
    }

    public final /* bridge */ /* synthetic */ cl writeShort(int i) throws IOException {
        an(i);
        return this;
    }

    public final /* bridge */ /* synthetic */ cl x(long j) throws IOException {
        aj(j);
        return this;
    }

    public final long y(cq cqVar) {
        long j;
        int i;
        int i2;
        qa qaVar = this.c;
        if (qaVar == null) {
            return -1;
        }
        long j2 = this.f;
        long j3 = 0;
        if (j2 - 0 >= 0) {
            j = 0;
            while (true) {
                long j4 = ((long) (qaVar.c - qaVar.b)) + j;
                if (j4 >= 0) {
                    break;
                }
                qaVar = qaVar.f;
                j = j4;
            }
        } else {
            while (j > 0) {
                qaVar = qaVar.g;
                j2 = j - ((long) (qaVar.c - qaVar.b));
            }
        }
        if (cqVar.t() == 2) {
            byte n = cqVar.n(0);
            byte n2 = cqVar.n(1);
            while (j < this.f) {
                byte[] bArr = qaVar.a;
                i2 = (int) ((((long) qaVar.b) + j3) - j);
                int i3 = qaVar.c;
                while (i2 < i3) {
                    byte b2 = bArr[i2];
                    if (b2 == n || b2 == n2) {
                        i = qaVar.b;
                    } else {
                        i2++;
                    }
                }
                j3 = ((long) (qaVar.c - qaVar.b)) + j;
                qaVar = qaVar.f;
                j = j3;
            }
            return -1;
        }
        byte[] p = cqVar.p();
        while (j < this.f) {
            byte[] bArr2 = qaVar.a;
            int i4 = (int) ((((long) qaVar.b) + j3) - j);
            int i5 = qaVar.c;
            while (i2 < i5) {
                byte b3 = bArr2[i2];
                int length = p.length;
                int i6 = 0;
                while (i6 < length) {
                    if (b3 == p[i6]) {
                        i = qaVar.b;
                    } else {
                        i6++;
                    }
                }
                i4 = i2 + 1;
            }
            j3 = ((long) (qaVar.c - qaVar.b)) + j;
            qaVar = qaVar.f;
            j = j3;
        }
        return -1;
        return ((long) (i2 - i)) + j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0080, code lost:
        if (r8 != r9) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0082, code lost:
        r15.c = r6.a();
        defpackage.sa.a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008c, code lost:
        r6.b = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008e, code lost:
        if (r1 != false) goto L_0x0094;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0068 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long z() {
        /*
            r15 = this;
            long r0 = r15.f
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x009b
            r0 = 0
            r4 = r2
            r1 = 0
        L_0x000b:
            qa r6 = r15.c
            byte[] r7 = r6.a
            int r8 = r6.b
            int r9 = r6.c
        L_0x0013:
            if (r8 >= r9) goto L_0x0080
            byte r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L_0x0022
            r11 = 57
            if (r10 > r11) goto L_0x0022
            int r11 = r10 + -48
            goto L_0x0039
        L_0x0022:
            r11 = 97
            if (r10 < r11) goto L_0x002d
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L_0x002d
            int r11 = r10 + -97
            goto L_0x0037
        L_0x002d:
            r11 = 65
            if (r10 < r11) goto L_0x0064
            r11 = 70
            if (r10 > r11) goto L_0x0064
            int r11 = r10 + -65
        L_0x0037:
            int r11 = r11 + 10
        L_0x0039:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r14 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r14 != 0) goto L_0x0049
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L_0x0013
        L_0x0049:
            ck r0 = new ck
            r0.<init>()
            r0.ak(r4)
            r0.ai(r10)
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.String r0 = r0.ae()
            java.lang.String r2 = "Number too large: "
            java.lang.String r0 = r2.concat(r0)
            r1.<init>(r0)
            throw r1
        L_0x0064:
            if (r0 == 0) goto L_0x0068
            r1 = 1
            goto L_0x0080
        L_0x0068:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Expected leading [0-9a-fA-F] character but was 0x"
            r1.<init>(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r10)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0080:
            if (r8 != r9) goto L_0x008c
            qa r7 = r6.a()
            r15.c = r7
            defpackage.sa.a(r6)
            goto L_0x008e
        L_0x008c:
            r6.b = r8
        L_0x008e:
            if (r1 != 0) goto L_0x0094
            qa r6 = r15.c
            if (r6 != 0) goto L_0x000b
        L_0x0094:
            long r1 = r15.f
            long r6 = (long) r0
            long r1 = r1 - r6
            r15.f = r1
            return r4
        L_0x009b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ck.z():long");
    }

    public final void write(byte[] bArr) {
        if (bArr != null) {
            write(bArr, 0, bArr.length);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final void write(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            long j = (long) i2;
            jd.a((long) bArr.length, (long) i, j);
            int i3 = i2 + i;
            while (i < i3) {
                qa ag = ag(1);
                int min = Math.min(i3 - i, 8192 - ag.c);
                System.arraycopy(bArr, i, ag.a, ag.c, min);
                i += min;
                ag.c += min;
            }
            this.f += j;
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final int read(ByteBuffer byteBuffer) throws IOException {
        qa qaVar = this.c;
        if (qaVar == null) {
            return -1;
        }
        int min = Math.min(byteBuffer.remaining(), qaVar.c - qaVar.b);
        byteBuffer.put(qaVar.a, qaVar.b, min);
        int i = qaVar.b + min;
        qaVar.b = i;
        this.f -= (long) min;
        if (i == qaVar.c) {
            this.c = qaVar.a();
            sa.a(qaVar);
        }
        return min;
    }

    public final int write(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer != null) {
            int remaining = byteBuffer.remaining();
            int i = remaining;
            while (i > 0) {
                qa ag = ag(1);
                int min = Math.min(i, 8192 - ag.c);
                byteBuffer.get(ag.a, ag.c, min);
                i -= min;
                ag.c += min;
            }
            this.f += (long) remaining;
            return remaining;
        }
        throw new IllegalArgumentException("source == null");
    }

    public final long read(ck ckVar, long j) {
        if (ckVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j >= 0) {
            long j2 = this.f;
            if (j2 == 0) {
                return -1;
            }
            if (j > j2) {
                j = j2;
            }
            ckVar.write(this, j);
            return j;
        } else {
            throw new IllegalArgumentException(y2.g("byteCount < 0: ", j));
        }
    }

    public final void write(ck ckVar, long j) {
        qa qaVar;
        int i;
        if (ckVar == null) {
            throw new IllegalArgumentException("source == null");
        } else if (ckVar != this) {
            jd.a(ckVar.f, 0, j);
            while (j > 0) {
                qa qaVar2 = ckVar.c;
                int i2 = qaVar2.c - qaVar2.b;
                int i3 = 0;
                if (j < ((long) i2)) {
                    qa qaVar3 = this.c;
                    qa qaVar4 = qaVar3 != null ? qaVar3.g : null;
                    if (qaVar4 != null && qaVar4.e) {
                        long j2 = ((long) qaVar4.c) + j;
                        if (qaVar4.d) {
                            i = 0;
                        } else {
                            i = qaVar4.b;
                        }
                        if (j2 - ((long) i) <= 8192) {
                            qaVar2.d(qaVar4, (int) j);
                            ckVar.f -= j;
                            this.f += j;
                            return;
                        }
                    }
                    int i4 = (int) j;
                    if (i4 <= 0 || i4 > i2) {
                        throw new IllegalArgumentException();
                    }
                    if (i4 >= 1024) {
                        qaVar = qaVar2.c();
                    } else {
                        qaVar = sa.b();
                        System.arraycopy(qaVar2.a, qaVar2.b, qaVar.a, 0, i4);
                    }
                    qaVar.c = qaVar.b + i4;
                    qaVar2.b += i4;
                    qaVar2.g.b(qaVar);
                    ckVar.c = qaVar;
                }
                qa qaVar5 = ckVar.c;
                long j3 = (long) (qaVar5.c - qaVar5.b);
                ckVar.c = qaVar5.a();
                qa qaVar6 = this.c;
                if (qaVar6 == null) {
                    this.c = qaVar5;
                    qaVar5.g = qaVar5;
                    qaVar5.f = qaVar5;
                } else {
                    qaVar6.g.b(qaVar5);
                    qa qaVar7 = qaVar5.g;
                    if (qaVar7 == qaVar5) {
                        throw new IllegalStateException();
                    } else if (qaVar7.e) {
                        int i5 = qaVar5.c - qaVar5.b;
                        int i6 = 8192 - qaVar7.c;
                        if (!qaVar7.d) {
                            i3 = qaVar7.b;
                        }
                        if (i5 <= i6 + i3) {
                            qaVar5.d(qaVar7, i5);
                            qaVar5.a();
                            sa.a(qaVar5);
                        }
                    }
                }
                ckVar.f -= j3;
                this.f += j3;
                j -= j3;
            }
        } else {
            throw new IllegalArgumentException("source == this");
        }
    }
}
