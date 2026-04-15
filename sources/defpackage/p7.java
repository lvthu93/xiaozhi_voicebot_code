package defpackage;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: p7  reason: default package */
public final class p7 implements jb {
    public final /* synthetic */ lc c;
    public final /* synthetic */ InputStream f;

    public p7(InputStream inputStream, lc lcVar) {
        this.c = lcVar;
        this.f = inputStream;
    }

    public final void close() throws IOException {
        this.f.close();
    }

    public final long read(ck ckVar, long j) throws IOException {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException(y2.g("byteCount < 0: ", j));
        } else if (i == 0) {
            return 0;
        } else {
            boolean z = true;
            try {
                this.c.throwIfReached();
                qa ag = ckVar.ag(1);
                int read = this.f.read(ag.a, ag.c, (int) Math.min(j, (long) (8192 - ag.c)));
                if (read == -1) {
                    return -1;
                }
                ag.c += read;
                long j2 = (long) read;
                ckVar.f += j2;
                return j2;
            } catch (AssertionError e) {
                if (e.getCause() == null || e.getMessage() == null || !e.getMessage().contains("getsockname failed")) {
                    z = false;
                }
                if (z) {
                    throw new IOException(e);
                }
                throw e;
            }
        }
    }

    public final lc timeout() {
        return this.c;
    }

    public final String toString() {
        return "source(" + this.f + ")";
    }
}
