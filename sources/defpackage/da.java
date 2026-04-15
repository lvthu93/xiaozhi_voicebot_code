package defpackage;

import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* renamed from: da  reason: default package */
public final class da implements cm {
    public final ck c = new ck();
    public final jb f;
    public boolean g;

    public da(jb jbVar) {
        if (jbVar != null) {
            this.f = jbVar;
            return;
        }
        throw new NullPointerException("source == null");
    }

    public final ck a() {
        return this.c;
    }

    public final boolean aa(cq cqVar) throws IOException {
        int t = cqVar.t();
        if (this.g) {
            throw new IllegalStateException("closed");
        } else if (t < 0 || cqVar.t() - 0 < t) {
            return false;
        } else {
            for (int i = 0; i < t; i++) {
                long j = ((long) i) + 0;
                if (!t(1 + j) || this.c.i(j) != cqVar.n(0 + i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public final cq b(long j) throws IOException {
        w(j);
        return this.c.b(j);
    }

    public final void close() throws IOException {
        if (!this.g) {
            this.g = true;
            this.f.close();
            this.c.clear();
        }
    }

    public final long d(byte b, long j, long j2) throws IOException {
        if (!this.g) {
            long j3 = 0;
            if (j2 >= 0) {
                while (j3 < j2) {
                    long n = this.c.n(b, j3, j2);
                    if (n == -1) {
                        ck ckVar = this.c;
                        long j4 = ckVar.f;
                        if (j4 >= j2 || this.f.read(ckVar, 8192) == -1) {
                            break;
                        }
                        j3 = Math.max(j3, j4);
                    } else {
                        return n;
                    }
                }
                return -1;
            }
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", new Object[]{0L, Long.valueOf(j2)}));
        }
        throw new IllegalStateException("closed");
    }

    public final byte[] f() throws IOException {
        jb jbVar = this.f;
        ck ckVar = this.c;
        ckVar.j(jbVar);
        return ckVar.f();
    }

    public final boolean g() throws IOException {
        if (!this.g) {
            ck ckVar = this.c;
            if (!ckVar.g() || this.f.read(ckVar, 8192) != -1) {
                return false;
            }
            return true;
        }
        throw new IllegalStateException("closed");
    }

    public final InputStream inputStream() {
        return new a();
    }

    public final boolean isOpen() {
        return !this.g;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long l() throws java.io.IOException {
        /*
            r7 = this;
            r0 = 1
            r7.w(r0)
            r0 = 0
            r1 = 0
        L_0x0007:
            int r2 = r1 + 1
            long r3 = (long) r2
            boolean r3 = r7.t(r3)
            ck r4 = r7.c
            if (r3 == 0) goto L_0x0040
            long r5 = (long) r1
            byte r3 = r4.i(r5)
            r5 = 48
            if (r3 < r5) goto L_0x001f
            r5 = 57
            if (r3 <= r5) goto L_0x0026
        L_0x001f:
            if (r1 != 0) goto L_0x0028
            r5 = 45
            if (r3 == r5) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r1 = r2
            goto L_0x0007
        L_0x0028:
            if (r1 == 0) goto L_0x002b
            goto L_0x0040
        L_0x002b:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Byte r3 = java.lang.Byte.valueOf(r3)
            r2[r0] = r3
            java.lang.String r0 = "Expected leading [0-9] or '-' character but was %#x"
            java.lang.String r0 = java.lang.String.format(r0, r2)
            r1.<init>(r0)
            throw r1
        L_0x0040:
            long r0 = r4.l()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.da.l():long");
    }

    public final String m(long j) throws IOException {
        long j2;
        long j3 = j;
        if (j3 >= 0) {
            if (j3 == Long.MAX_VALUE) {
                j2 = Long.MAX_VALUE;
            } else {
                j2 = j3 + 1;
            }
            long d = d((byte) 10, 0, j2);
            ck ckVar = this.c;
            if (d != -1) {
                return ckVar.af(d);
            }
            if (j2 < Long.MAX_VALUE && t(j2) && ckVar.i(j2 - 1) == 13 && t(1 + j2) && ckVar.i(j2) == 10) {
                return ckVar.af(j2);
            }
            ck ckVar2 = new ck();
            ckVar.h(0, ckVar2, Math.min(32, ckVar.f));
            throw new EOFException("\\n not found: limit=" + Math.min(ckVar.f, j3) + " content=" + ckVar2.ac().o() + 8230);
        }
        throw new IllegalArgumentException(y2.g("limit < 0: ", j3));
    }

    public final void p(ck ckVar, long j) throws IOException {
        ck ckVar2 = this.c;
        try {
            w(j);
            ckVar2.p(ckVar, j);
        } catch (EOFException e) {
            ckVar.j(ckVar2);
            throw e;
        }
    }

    public final String q(Charset charset) throws IOException {
        if (charset != null) {
            jb jbVar = this.f;
            ck ckVar = this.c;
            ckVar.j(jbVar);
            return ckVar.q(charset);
        }
        throw new IllegalArgumentException("charset == null");
    }

    public final long read(ck ckVar, long j) throws IOException {
        if (ckVar == null) {
            throw new IllegalArgumentException("sink == null");
        } else if (j < 0) {
            throw new IllegalArgumentException(y2.g("byteCount < 0: ", j));
        } else if (!this.g) {
            ck ckVar2 = this.c;
            if (ckVar2.f == 0 && this.f.read(ckVar2, 8192) == -1) {
                return -1;
            }
            return ckVar2.read(ckVar, Math.min(j, ckVar2.f));
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public final byte readByte() throws IOException {
        w(1);
        return this.c.readByte();
    }

    public final void readFully(byte[] bArr) throws IOException {
        ck ckVar = this.c;
        try {
            w((long) bArr.length);
            ckVar.readFully(bArr);
        } catch (EOFException e) {
            int i = 0;
            while (true) {
                long j = ckVar.f;
                if (j > 0) {
                    int read = ckVar.read(bArr, i, (int) j);
                    if (read != -1) {
                        i += read;
                    } else {
                        throw new AssertionError();
                    }
                } else {
                    throw e;
                }
            }
        }
    }

    public final int readInt() throws IOException {
        w(4);
        return this.c.readInt();
    }

    public final long readLong() throws IOException {
        w(8);
        return this.c.readLong();
    }

    public final short readShort() throws IOException {
        w(2);
        return this.c.readShort();
    }

    public final void skip(long j) throws IOException {
        if (!this.g) {
            while (j > 0) {
                ck ckVar = this.c;
                if (ckVar.f == 0 && this.f.read(ckVar, 8192) == -1) {
                    throw new EOFException();
                }
                long min = Math.min(j, ckVar.f);
                ckVar.skip(min);
                j -= min;
            }
            return;
        }
        throw new IllegalStateException("closed");
    }

    public final boolean t(long j) throws IOException {
        ck ckVar;
        if (j < 0) {
            throw new IllegalArgumentException(y2.g("byteCount < 0: ", j));
        } else if (!this.g) {
            do {
                ckVar = this.c;
                if (ckVar.f >= j) {
                    return true;
                }
            } while (this.f.read(ckVar, 8192) != -1);
            return false;
        } else {
            throw new IllegalStateException("closed");
        }
    }

    public final lc timeout() {
        return this.f.timeout();
    }

    public final String toString() {
        return "buffer(" + this.f + ")";
    }

    public final String u() throws IOException {
        return m(Long.MAX_VALUE);
    }

    public final byte[] v(long j) throws IOException {
        w(j);
        return this.c.v(j);
    }

    public final void w(long j) throws IOException {
        if (!t(j)) {
            throw new EOFException();
        }
    }

    public final long z() throws IOException {
        ck ckVar;
        w(1);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            boolean t = t((long) i2);
            ckVar = this.c;
            if (!t) {
                break;
            }
            byte i3 = ckVar.i((long) i);
            if ((i3 >= 48 && i3 <= 57) || ((i3 >= 97 && i3 <= 102) || (i3 >= 65 && i3 <= 70))) {
                i = i2;
            } else if (i == 0) {
                throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", new Object[]{Byte.valueOf(i3)}));
            }
        }
        return ckVar.z();
    }

    /* renamed from: da$a */
    public class a extends InputStream implements InputStreamRetargetInterface {
        public a() {
        }

        public final int available() throws IOException {
            da daVar = da.this;
            if (!daVar.g) {
                return (int) Math.min(daVar.c.f, 2147483647L);
            }
            throw new IOException("closed");
        }

        public final void close() throws IOException {
            da.this.close();
        }

        public final int read() throws IOException {
            da daVar = da.this;
            if (!daVar.g) {
                ck ckVar = daVar.c;
                if (ckVar.f == 0 && daVar.f.read(ckVar, 8192) == -1) {
                    return -1;
                }
                return daVar.c.readByte() & 255;
            }
            throw new IOException("closed");
        }

        public final String toString() {
            return da.this + ".inputStream()";
        }

        public final /* synthetic */ long transferTo(OutputStream outputStream) {
            return DesugarInputStream.transferTo(this, outputStream);
        }

        public final int read(byte[] bArr, int i, int i2) throws IOException {
            da daVar = da.this;
            if (!daVar.g) {
                jd.a((long) bArr.length, (long) i, (long) i2);
                ck ckVar = daVar.c;
                if (ckVar.f == 0 && daVar.f.read(ckVar, 8192) == -1) {
                    return -1;
                }
                return daVar.c.read(bArr, i, i2);
            }
            throw new IOException("closed");
        }
    }

    public final int read(ByteBuffer byteBuffer) throws IOException {
        ck ckVar = this.c;
        if (ckVar.f == 0 && this.f.read(ckVar, 8192) == -1) {
            return -1;
        }
        return ckVar.read(byteBuffer);
    }
}
