package defpackage;

import com.google.protobuf.p;
import com.google.protobuf.x;
import defpackage.gd;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: n0  reason: default package */
public abstract class n0 extends co {
    public static final Logger f = Logger.getLogger(n0.class.getName());
    public static final boolean g = fd.e;
    public o0 c;

    /* renamed from: n0$a */
    public static abstract class a extends n0 {
        public final byte[] h;
        public final int i;
        public int j;

        public a(int i2) {
            if (i2 >= 0) {
                byte[] bArr = new byte[Math.max(i2, 20)];
                this.h = bArr;
                this.i = bArr.length;
                return;
            }
            throw new IllegalArgumentException("bufferSize must be >= 0");
        }

        public final void ap(int i2) {
            int i3 = this.j;
            int i4 = i3 + 1;
            byte[] bArr = this.h;
            bArr[i3] = (byte) i2;
            int i5 = i4 + 1;
            bArr[i4] = (byte) (i2 >> 8);
            int i6 = i5 + 1;
            bArr[i5] = (byte) (i2 >> 16);
            bArr[i6] = (byte) (i2 >> 24);
            this.j = i6 + 1;
        }

        public final void aq(long j2) {
            int i2 = this.j;
            int i3 = i2 + 1;
            byte[] bArr = this.h;
            bArr[i2] = (byte) ((int) j2);
            int i4 = i3 + 1;
            bArr[i3] = (byte) ((int) (j2 >> 8));
            int i5 = i4 + 1;
            bArr[i4] = (byte) ((int) (j2 >> 16));
            int i6 = i5 + 1;
            bArr[i5] = (byte) ((int) (j2 >> 24));
            int i7 = i6 + 1;
            bArr[i6] = (byte) ((int) (j2 >> 32));
            int i8 = i7 + 1;
            bArr[i7] = (byte) ((int) (j2 >> 40));
            int i9 = i8 + 1;
            bArr[i8] = (byte) ((int) (j2 >> 48));
            bArr[i9] = (byte) ((int) (j2 >> 56));
            this.j = i9 + 1;
        }

        public final void ar(int i2, int i3) {
            as((i2 << 3) | i3);
        }

        public final void as(int i2) {
            boolean z = n0.g;
            byte[] bArr = this.h;
            if (z) {
                while ((i2 & -128) != 0) {
                    int i3 = this.j;
                    this.j = i3 + 1;
                    fd.s(bArr, (long) i3, (byte) (i2 | 128));
                    i2 >>>= 7;
                }
                int i4 = this.j;
                this.j = i4 + 1;
                fd.s(bArr, (long) i4, (byte) i2);
                return;
            }
            while ((i2 & -128) != 0) {
                int i5 = this.j;
                this.j = i5 + 1;
                bArr[i5] = (byte) (i2 | 128);
                i2 >>>= 7;
            }
            int i6 = this.j;
            this.j = i6 + 1;
            bArr[i6] = (byte) i2;
        }

        public final void at(long j2) {
            boolean z = n0.g;
            byte[] bArr = this.h;
            if (z) {
                while ((j2 & -128) != 0) {
                    int i2 = this.j;
                    this.j = i2 + 1;
                    fd.s(bArr, (long) i2, (byte) (((int) j2) | 128));
                    j2 >>>= 7;
                }
                int i3 = this.j;
                this.j = i3 + 1;
                fd.s(bArr, (long) i3, (byte) ((int) j2));
                return;
            }
            while ((j2 & -128) != 0) {
                int i4 = this.j;
                this.j = i4 + 1;
                bArr[i4] = (byte) (((int) j2) | 128);
                j2 >>>= 7;
            }
            int i5 = this.j;
            this.j = i5 + 1;
            bArr[i5] = (byte) ((int) j2);
        }
    }

    /* renamed from: n0$b */
    public static class b extends n0 {
        public final byte[] h;
        public final int i;
        public int j;

        public b(byte[] bArr, int i2) {
            int i3 = 0 + i2;
            if ((0 | i2 | (bArr.length - i3)) >= 0) {
                this.h = bArr;
                this.j = 0;
                this.i = i3;
                return;
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(bArr.length), 0, Integer.valueOf(i2)}));
        }

        public final void a(byte[] bArr, int i2, int i3) throws IOException {
            try {
                System.arraycopy(bArr, i2, this.h, this.j, i3);
                this.j += i3;
            } catch (IndexOutOfBoundsException e) {
                throw new c(this.j, this.i, i3, e);
            }
        }

        public final void aa(int i2, cp cpVar) throws IOException {
            ak(i2, 2);
            aq(cpVar);
        }

        public final void ab(int i2, int i3) throws IOException {
            ak(i2, 5);
            ac(i3);
        }

        public final void ac(int i2) throws IOException {
            int i3 = this.j;
            try {
                byte[] bArr = this.h;
                bArr[i3] = (byte) i2;
                bArr[i3 + 1] = (byte) (i2 >> 8);
                bArr[i3 + 2] = (byte) (i2 >> 16);
                bArr[i3 + 3] = (byte) (i2 >> 24);
                this.j = i3 + 4;
            } catch (IndexOutOfBoundsException e) {
                throw new c(i3, this.i, 4, e);
            }
        }

        public final void ad(int i2, long j2) throws IOException {
            ak(i2, 1);
            ae(j2);
        }

        public final void ae(long j2) throws IOException {
            int i2 = this.j;
            try {
                byte[] bArr = this.h;
                bArr[i2] = (byte) ((int) j2);
                bArr[i2 + 1] = (byte) ((int) (j2 >> 8));
                bArr[i2 + 2] = (byte) ((int) (j2 >> 16));
                bArr[i2 + 3] = (byte) ((int) (j2 >> 24));
                bArr[i2 + 4] = (byte) ((int) (j2 >> 32));
                bArr[i2 + 5] = (byte) ((int) (j2 >> 40));
                bArr[i2 + 6] = (byte) ((int) (j2 >> 48));
                bArr[i2 + 7] = (byte) ((int) (j2 >> 56));
                this.j = i2 + 8;
            } catch (IndexOutOfBoundsException e) {
                throw new c(i2, this.i, 8, e);
            }
        }

        public final void af(int i2, int i3) throws IOException {
            ak(i2, 0);
            ag(i3);
        }

        public final void ag(int i2) throws IOException {
            if (i2 >= 0) {
                am(i2);
            } else {
                ao((long) i2);
            }
        }

        public final void ah(int i2, x xVar) throws IOException {
            ak(1, 3);
            al(2, i2);
            ak(3, 2);
            ar(xVar);
            ak(1, 4);
        }

        public final void ai(int i2, cp cpVar) throws IOException {
            ak(1, 3);
            al(2, i2);
            aa(3, cpVar);
            ak(1, 4);
        }

        public final void aj(int i2, String str) throws IOException {
            ak(i2, 2);
            as(str);
        }

        public final void ak(int i2, int i3) throws IOException {
            am((i2 << 3) | i3);
        }

        public final void al(int i2, int i3) throws IOException {
            ak(i2, 0);
            am(i3);
        }

        public final void am(int i2) throws IOException {
            int i3;
            int i4 = this.j;
            while (true) {
                int i5 = i2 & -128;
                byte[] bArr = this.h;
                if (i5 == 0) {
                    i3 = i4 + 1;
                    try {
                        bArr[i4] = (byte) i2;
                        this.j = i3;
                        return;
                    } catch (IndexOutOfBoundsException e) {
                        throw new c(i3, this.i, 1, e);
                    }
                } else {
                    i3 = i4 + 1;
                    bArr[i4] = (byte) (i2 | 128);
                    i2 >>>= 7;
                    i4 = i3;
                }
            }
        }

        public final void an(int i2, long j2) throws IOException {
            ak(i2, 0);
            ao(j2);
        }

        public final void ao(long j2) throws IOException {
            int i2;
            int i3 = this.j;
            boolean z = n0.g;
            int i4 = this.i;
            byte[] bArr = this.h;
            if (!z || i4 - i3 < 10) {
                while ((j2 & -128) != 0) {
                    i2 = i3 + 1;
                    bArr[i3] = (byte) (((int) j2) | 128);
                    j2 >>>= 7;
                    i3 = i2;
                }
                i2 = i3 + 1;
                try {
                    bArr[i3] = (byte) ((int) j2);
                } catch (IndexOutOfBoundsException e) {
                    throw new c(i2, i4, 1, e);
                }
            } else {
                while ((j2 & -128) != 0) {
                    fd.s(bArr, (long) i3, (byte) (((int) j2) | 128));
                    j2 >>>= 7;
                    i3++;
                }
                i2 = i3 + 1;
                fd.s(bArr, (long) i3, (byte) ((int) j2));
            }
            this.j = i2;
        }

        public final int ap() {
            return this.i - this.j;
        }

        public final void aq(cp cpVar) throws IOException {
            am(cpVar.size());
            cpVar.s(this);
        }

        public final void ar(x xVar) throws IOException {
            am(xVar.getSerializedSize());
            xVar.writeTo(this);
        }

        public final void as(String str) throws IOException {
            int i2 = this.j;
            try {
                int u = n0.u(str.length() * 3);
                int u2 = n0.u(str.length());
                int i3 = this.i;
                byte[] bArr = this.h;
                if (u2 == u) {
                    int i4 = i2 + u2;
                    this.j = i4;
                    int d = gd.a.d(str, bArr, i4, i3 - i4);
                    this.j = i2;
                    am((d - i2) - u2);
                    this.j = d;
                    return;
                }
                am(gd.c(str));
                int i5 = this.j;
                this.j = gd.a.d(str, bArr, i5, i3 - i5);
            } catch (gd.d e) {
                this.j = i2;
                x(str, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new c(e2);
            }
        }

        public final void y(byte b) throws IOException {
            int i2 = this.j;
            try {
                int i3 = i2 + 1;
                try {
                    this.h[i2] = b;
                    this.j = i3;
                } catch (IndexOutOfBoundsException e) {
                    e = e;
                    i2 = i3;
                    throw new c(i2, this.i, 1, e);
                }
            } catch (IndexOutOfBoundsException e2) {
                e = e2;
                throw new c(i2, this.i, 1, e);
            }
        }

        public final void z(int i2, boolean z) throws IOException {
            ak(i2, 0);
            y(z ? (byte) 1 : 0);
        }
    }

    /* renamed from: n0$c */
    public static class c extends IOException {
        private static final long serialVersionUID = -6947486886997889499L;

        public c(IndexOutOfBoundsException indexOutOfBoundsException) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", indexOutOfBoundsException);
        }

        public c(int i, int i2, int i3, IndexOutOfBoundsException indexOutOfBoundsException) {
            super(y2.i("CodedOutputStream was writing to a flat byte array and ran out of space.: ", String.format(Locale.US, "Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf((long) i), Long.valueOf((long) i2), Integer.valueOf(i3)})), indexOutOfBoundsException);
        }
    }

    /* renamed from: n0$d */
    public static final class d extends a {
        public final OutputStream k;

        public d(OutputStream outputStream, int i) {
            super(i);
            if (outputStream != null) {
                this.k = outputStream;
                return;
            }
            throw new NullPointerException("out");
        }

        public final void a(byte[] bArr, int i, int i2) throws IOException {
            aw(bArr, i, i2);
        }

        public final void aa(int i, cp cpVar) throws IOException {
            ak(i, 2);
            ax(cpVar);
        }

        public final void ab(int i, int i2) throws IOException {
            av(14);
            ar(i, 5);
            ap(i2);
        }

        public final void ac(int i) throws IOException {
            av(4);
            ap(i);
        }

        public final void ad(int i, long j) throws IOException {
            av(18);
            ar(i, 1);
            aq(j);
        }

        public final void ae(long j) throws IOException {
            av(8);
            aq(j);
        }

        public final void af(int i, int i2) throws IOException {
            av(20);
            ar(i, 0);
            if (i2 >= 0) {
                as(i2);
            } else {
                at((long) i2);
            }
        }

        public final void ag(int i) throws IOException {
            if (i >= 0) {
                am(i);
            } else {
                ao((long) i);
            }
        }

        public final void ah(int i, x xVar) throws IOException {
            ak(1, 3);
            al(2, i);
            ak(3, 2);
            ay(xVar);
            ak(1, 4);
        }

        public final void ai(int i, cp cpVar) throws IOException {
            ak(1, 3);
            al(2, i);
            aa(3, cpVar);
            ak(1, 4);
        }

        public final void aj(int i, String str) throws IOException {
            ak(i, 2);
            az(str);
        }

        public final void ak(int i, int i2) throws IOException {
            am((i << 3) | i2);
        }

        public final void al(int i, int i2) throws IOException {
            av(20);
            ar(i, 0);
            as(i2);
        }

        public final void am(int i) throws IOException {
            av(5);
            as(i);
        }

        public final void an(int i, long j) throws IOException {
            av(20);
            ar(i, 0);
            at(j);
        }

        public final void ao(long j) throws IOException {
            av(10);
            at(j);
        }

        public final void au() throws IOException {
            this.k.write(this.h, 0, this.j);
            this.j = 0;
        }

        public final void av(int i) throws IOException {
            if (this.i - this.j < i) {
                au();
            }
        }

        public final void aw(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.j;
            int i4 = this.i;
            int i5 = i4 - i3;
            byte[] bArr2 = this.h;
            if (i5 >= i2) {
                System.arraycopy(bArr, i, bArr2, i3, i2);
                this.j += i2;
                return;
            }
            System.arraycopy(bArr, i, bArr2, i3, i5);
            int i6 = i + i5;
            int i7 = i2 - i5;
            this.j = i4;
            au();
            if (i7 <= i4) {
                System.arraycopy(bArr, i6, bArr2, 0, i7);
                this.j = i7;
                return;
            }
            this.k.write(bArr, i6, i7);
        }

        public final void ax(cp cpVar) throws IOException {
            am(cpVar.size());
            cpVar.s(this);
        }

        public final void ay(x xVar) throws IOException {
            am(xVar.getSerializedSize());
            xVar.writeTo(this);
        }

        public final void az(String str) throws IOException {
            int i;
            try {
                int length = str.length() * 3;
                int u = n0.u(length);
                int i2 = u + length;
                int i3 = this.i;
                if (i2 > i3) {
                    byte[] bArr = new byte[length];
                    int d = gd.a.d(str, bArr, 0, length);
                    am(d);
                    aw(bArr, 0, d);
                    return;
                }
                if (i2 > i3 - this.j) {
                    au();
                }
                int u2 = n0.u(str.length());
                i = this.j;
                byte[] bArr2 = this.h;
                if (u2 == u) {
                    int i4 = i + u2;
                    this.j = i4;
                    int d2 = gd.a.d(str, bArr2, i4, i3 - i4);
                    this.j = i;
                    as((d2 - i) - u2);
                    this.j = d2;
                    return;
                }
                int c = gd.c(str);
                as(c);
                this.j = gd.a.d(str, bArr2, this.j, c);
            } catch (gd.d e) {
                this.j = i;
                throw e;
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new c(e2);
            } catch (gd.d e3) {
                x(str, e3);
            }
        }

        public final void y(byte b) throws IOException {
            if (this.j == this.i) {
                au();
            }
            int i = this.j;
            this.h[i] = b;
            this.j = i + 1;
        }

        public final void z(int i, boolean z) throws IOException {
            av(11);
            ar(i, 0);
            byte b = z ? (byte) 1 : 0;
            int i2 = this.j;
            this.h[i2] = b;
            this.j = i2 + 1;
        }
    }

    public static int b(int i) {
        return s(i) + 1;
    }

    public static int c(int i, cp cpVar) {
        int s = s(i);
        int size = cpVar.size();
        return u(size) + size + s;
    }

    public static int d(int i) {
        return s(i) + 8;
    }

    public static int e(int i, int i2) {
        return w((long) i2) + s(i);
    }

    public static int f(int i) {
        return s(i) + 4;
    }

    public static int g(int i) {
        return s(i) + 8;
    }

    public static int h(int i) {
        return s(i) + 4;
    }

    public static int i(int i, int i2) {
        return w((long) i2) + s(i);
    }

    public static int j(int i, long j) {
        return w(j) + s(i);
    }

    public static int k(int i) {
        return s(i) + 4;
    }

    public static int l(int i) {
        return s(i) + 8;
    }

    public static int m(int i, int i2) {
        return n(i2) + s(i);
    }

    public static int n(int i) {
        return u((i >> 31) ^ (i << 1));
    }

    public static int o(int i, long j) {
        return p(j) + s(i);
    }

    public static int p(long j) {
        return w((j >> 63) ^ (j << 1));
    }

    public static int q(int i, String str) {
        return r(str) + s(i);
    }

    public static int r(String str) {
        int i;
        try {
            i = gd.c(str);
        } catch (gd.d unused) {
            i = str.getBytes(p.a).length;
        }
        return u(i) + i;
    }

    public static int s(int i) {
        return u((i << 3) | 0);
    }

    public static int t(int i, int i2) {
        return u(i2) + s(i);
    }

    public static int u(int i) {
        return (352 - (Integer.numberOfLeadingZeros(i) * 9)) >>> 6;
    }

    public static int v(int i, long j) {
        return w(j) + s(i);
    }

    public static int w(long j) {
        return (640 - (Long.numberOfLeadingZeros(j) * 9)) >>> 6;
    }

    public abstract void aa(int i, cp cpVar) throws IOException;

    public abstract void ab(int i, int i2) throws IOException;

    public abstract void ac(int i) throws IOException;

    public abstract void ad(int i, long j) throws IOException;

    public abstract void ae(long j) throws IOException;

    public abstract void af(int i, int i2) throws IOException;

    public abstract void ag(int i) throws IOException;

    public abstract void ah(int i, x xVar) throws IOException;

    public abstract void ai(int i, cp cpVar) throws IOException;

    public abstract void aj(int i, String str) throws IOException;

    public abstract void ak(int i, int i2) throws IOException;

    public abstract void al(int i, int i2) throws IOException;

    public abstract void am(int i) throws IOException;

    public abstract void an(int i, long j) throws IOException;

    public abstract void ao(long j) throws IOException;

    public final void x(String str, gd.d dVar) throws IOException {
        f.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", dVar);
        byte[] bytes = str.getBytes(p.a);
        try {
            am(bytes.length);
            a(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new c(e);
        }
    }

    public abstract void y(byte b2) throws IOException;

    public abstract void z(int i, boolean z) throws IOException;
}
