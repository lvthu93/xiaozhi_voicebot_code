package defpackage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* renamed from: z9  reason: default package */
public final class z9 implements cl {
    public final ck c = new ck();
    public final za f;
    public boolean g;

    public z9(za zaVar) {
        if (zaVar != null) {
            this.f = zaVar;
            return;
        }
        throw new NullPointerException("sink == null");
    }

    public final ck a() {
        return this.c;
    }

    public final cl c() throws IOException {
        if (!this.g) {
            ck ckVar = this.c;
            long j = ckVar.f;
            if (j > 0) {
                this.f.write(ckVar, j);
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final void close() throws IOException {
        za zaVar = this.f;
        if (!this.g) {
            try {
                ck ckVar = this.c;
                long j = ckVar.f;
                if (j > 0) {
                    zaVar.write(ckVar, j);
                }
                th = null;
            } catch (Throwable th) {
                th = th;
            }
            try {
                zaVar.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                }
            }
            this.g = true;
            if (th != null) {
                Charset charset = jd.a;
                throw th;
            }
        }
    }

    public final void flush() throws IOException {
        if (!this.g) {
            ck ckVar = this.c;
            long j = ckVar.f;
            za zaVar = this.f;
            if (j > 0) {
                zaVar.write(ckVar, j);
            }
            zaVar.flush();
            return;
        }
        throw new IllegalStateException("closed");
    }

    public final boolean isOpen() {
        return !this.g;
    }

    public final long j(jb jbVar) throws IOException {
        long j = 0;
        while (true) {
            long read = ((p7) jbVar).read(this.c, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
            k();
        }
    }

    public final cl k() throws IOException {
        if (!this.g) {
            ck ckVar = this.c;
            long e = ckVar.e();
            if (e > 0) {
                this.f.write(ckVar, e);
            }
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl o(String str) throws IOException {
        if (!this.g) {
            ck ckVar = this.c;
            ckVar.getClass();
            ckVar.ap(0, str.length(), str);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl r(cq cqVar) throws IOException {
        if (!this.g) {
            this.c.ah(cqVar);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl s(long j) throws IOException {
        if (!this.g) {
            this.c.ak(j);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final lc timeout() {
        return this.f.timeout();
    }

    public final String toString() {
        return "buffer(" + this.f + ")";
    }

    public final void write(ck ckVar, long j) throws IOException {
        if (!this.g) {
            this.c.write(ckVar, j);
            k();
            return;
        }
        throw new IllegalStateException("closed");
    }

    public final cl writeByte(int i) throws IOException {
        if (!this.g) {
            this.c.ai(i);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl writeInt(int i) throws IOException {
        if (!this.g) {
            this.c.al(i);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl writeShort(int i) throws IOException {
        if (!this.g) {
            this.c.an(i);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl x(long j) throws IOException {
        if (!this.g) {
            this.c.aj(j);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl write(byte[] bArr) throws IOException {
        if (!this.g) {
            this.c.write(bArr);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final cl write(byte[] bArr, int i, int i2) throws IOException {
        if (!this.g) {
            this.c.write(bArr, i, i2);
            k();
            return this;
        }
        throw new IllegalStateException("closed");
    }

    public final int write(ByteBuffer byteBuffer) throws IOException {
        if (!this.g) {
            int write = this.c.write(byteBuffer);
            k();
            return write;
        }
        throw new IllegalStateException("closed");
    }
}
