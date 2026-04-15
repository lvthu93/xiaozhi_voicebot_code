package defpackage;

import java.io.IOException;

/* renamed from: q7  reason: default package */
public final class q7 implements za {
    public final void close() throws IOException {
    }

    public final void flush() throws IOException {
    }

    public final lc timeout() {
        return lc.NONE;
    }

    public final void write(ck ckVar, long j) throws IOException {
        ckVar.skip(j);
    }
}
