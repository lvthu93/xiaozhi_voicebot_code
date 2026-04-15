package defpackage;

import com.google.protobuf.p;
import j$.util.Iterator;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/* renamed from: cp  reason: default package */
public abstract class cp implements Iterable<Byte>, Serializable {
    public static final h f = new h(p.b);
    public static final e g;
    private static final long serialVersionUID = 1;
    public int c = 0;

    /* renamed from: cp$a */
    public class a extends b {
        public int c = 0;
        public final int f;

        public a() {
            this.f = cp.this.size();
        }

        public final byte b() {
            int i = this.c;
            if (i < this.f) {
                this.c = i + 1;
                return cp.this.j(i);
            }
            throw new NoSuchElementException();
        }

        public final boolean hasNext() {
            return this.c < this.f;
        }
    }

    /* renamed from: cp$b */
    public static abstract class b implements f, Iterator {
        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public final Object next() {
            return Byte.valueOf(b());
        }

        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* renamed from: cp$c */
    public static final class c implements e {
        public final byte[] a(byte[] bArr, int i, int i2) {
            return Arrays.copyOfRange(bArr, i, i2 + i);
        }
    }

    /* renamed from: cp$d */
    public static final class d extends h {
        private static final long serialVersionUID = 1;
        public final int i;
        public final int j;

        public d(byte[] bArr, int i2, int i3) {
            super(bArr);
            cp.c(i2, i2 + i3, bArr.length);
            this.i = i2;
            this.j = i3;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }

        public final byte a(int i2) {
            cp.b(i2, this.j);
            return this.h[this.i + i2];
        }

        public final void g(int i2, int i3, int i4, byte[] bArr) {
            System.arraycopy(this.h, this.i + i2, bArr, i3, i4);
        }

        public final byte j(int i2) {
            return this.h[this.i + i2];
        }

        public final int size() {
            return this.j;
        }

        public final int u() {
            return this.i;
        }

        public Object writeReplace() {
            return new h(q());
        }
    }

    /* renamed from: cp$e */
    public interface e {
        byte[] a(byte[] bArr, int i, int i2);
    }

    /* renamed from: cp$f */
    public interface f extends java.util.Iterator<Byte> {
        byte b();
    }

    /* renamed from: cp$g */
    public static abstract class g extends cp {
        private static final long serialVersionUID = 1;

        public final java.util.Iterator iterator() {
            return new a();
        }
    }

    /* renamed from: cp$h */
    public static class h extends g {
        private static final long serialVersionUID = 1;
        public final byte[] h;

        public h(byte[] bArr) {
            bArr.getClass();
            this.h = bArr;
        }

        public byte a(int i) {
            return this.h[i];
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof cp) || size() != ((cp) obj).size()) {
                return false;
            }
            if (size() == 0) {
                return true;
            }
            if (!(obj instanceof h)) {
                return obj.equals(this);
            }
            h hVar = (h) obj;
            int i = this.c;
            int i2 = hVar.c;
            if (i == 0 || i2 == 0 || i == i2) {
                return t(hVar, 0, size());
            }
            return false;
        }

        public void g(int i, int i2, int i3, byte[] bArr) {
            System.arraycopy(this.h, i, bArr, i2, i3);
        }

        public byte j(int i) {
            return this.h[i];
        }

        public final boolean k() {
            int u = u();
            return gd.f(this.h, u, size() + u);
        }

        public final com.google.protobuf.f m() {
            return com.google.protobuf.f.f(this.h, u(), size(), true);
        }

        public final int o(int i, int i2, int i3) {
            int u = u() + i2;
            Charset charset = p.a;
            for (int i4 = u; i4 < u + i3; i4++) {
                i = (i * 31) + this.h[i4];
            }
            return i;
        }

        public final cp p(int i, int i2) {
            int c = cp.c(i, i2, size());
            if (c == 0) {
                return cp.f;
            }
            return new d(this.h, u() + i, c);
        }

        public final String r(Charset charset) {
            return new String(this.h, u(), size(), charset);
        }

        public final void s(co coVar) throws IOException {
            coVar.a(this.h, u(), size());
        }

        public int size() {
            return this.h.length;
        }

        public final boolean t(cp cpVar, int i, int i2) {
            if (i2 <= cpVar.size()) {
                int i3 = i + i2;
                if (i3 > cpVar.size()) {
                    throw new IllegalArgumentException("Ran off end of other: " + i + ", " + i2 + ", " + cpVar.size());
                } else if (!(cpVar instanceof h)) {
                    return cpVar.p(i, i3).equals(p(0, i2));
                } else {
                    h hVar = (h) cpVar;
                    int u = u() + i2;
                    int u2 = u();
                    int u3 = hVar.u() + i;
                    while (u2 < u) {
                        if (this.h[u2] != hVar.h[u3]) {
                            return false;
                        }
                        u2++;
                        u3++;
                    }
                    return true;
                }
            } else {
                throw new IllegalArgumentException("Length too large: " + i2 + size());
            }
        }

        public int u() {
            return 0;
        }
    }

    /* renamed from: cp$i */
    public static final class i implements e {
        public final byte[] a(byte[] bArr, int i, int i2) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            return bArr2;
        }
    }

    static {
        e eVar;
        if (bb.a()) {
            eVar = new i();
        } else {
            eVar = new c();
        }
        g = eVar;
    }

    public static void b(int i2, int i3) {
        if (((i3 - (i2 + 1)) | i2) >= 0) {
            return;
        }
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(y2.f("Index < 0: ", i2));
        }
        throw new ArrayIndexOutOfBoundsException("Index > length: " + i2 + ", " + i3);
    }

    public static int c(int i2, int i3, int i4) {
        int i5 = i3 - i2;
        if ((i2 | i3 | i5 | (i4 - i3)) >= 0) {
            return i5;
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Beginning index: " + i2 + " < 0");
        } else if (i3 < i2) {
            throw new IndexOutOfBoundsException("Beginning index larger than ending index: " + i2 + ", " + i3);
        } else {
            throw new IndexOutOfBoundsException("End index: " + i3 + " >= " + i4);
        }
    }

    public static h f(byte[] bArr, int i2, int i3) {
        c(i2, i2 + i3, bArr.length);
        return new h(g.a(bArr, i2, i3));
    }

    public abstract byte a(int i2);

    public abstract boolean equals(Object obj);

    public abstract void g(int i2, int i3, int i4, byte[] bArr);

    public final int hashCode() {
        int i2 = this.c;
        if (i2 == 0) {
            int size = size();
            i2 = o(size, 0, size);
            if (i2 == 0) {
                i2 = 1;
            }
            this.c = i2;
        }
        return i2;
    }

    public abstract byte j(int i2);

    public abstract boolean k();

    /* renamed from: l */
    public f iterator() {
        return new a();
    }

    public abstract com.google.protobuf.f m();

    public abstract int o(int i2, int i3, int i4);

    public abstract cp p(int i2, int i3);

    public final byte[] q() {
        int size = size();
        if (size == 0) {
            return p.b;
        }
        byte[] bArr = new byte[size];
        g(0, 0, size, bArr);
        return bArr;
    }

    public abstract String r(Charset charset);

    public abstract void s(co coVar) throws IOException;

    public abstract int size();

    public final String toString() {
        String str;
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.toHexString(System.identityHashCode(this));
        objArr[1] = Integer.valueOf(size());
        if (size() <= 50) {
            str = cg.g(this);
        } else {
            str = cg.g(p(0, 47)) + "...";
        }
        objArr[2] = str;
        return String.format(locale, "<ByteString@%s size=%d contents=\"%s\">", objArr);
    }
}
