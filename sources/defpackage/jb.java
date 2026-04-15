package defpackage;

import java.io.Closeable;
import java.io.IOException;

/* renamed from: jb  reason: default package */
public interface jb extends Closeable {
    void close() throws IOException;

    long read(ck ckVar, long j) throws IOException;

    lc timeout();
}
