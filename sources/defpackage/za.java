package defpackage;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* renamed from: za  reason: default package */
public interface za extends Closeable, Flushable {
    void close() throws IOException;

    void flush() throws IOException;

    lc timeout();

    void write(ck ckVar, long j) throws IOException;
}
