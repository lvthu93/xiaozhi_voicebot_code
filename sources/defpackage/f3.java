package defpackage;

import java.io.IOException;

/* renamed from: f3  reason: default package */
public abstract class f3 implements jb {
    private final jb delegate;

    public f3(jb jbVar) {
        if (jbVar != null) {
            this.delegate = jbVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public final jb delegate() {
        return this.delegate;
    }

    public long read(ck ckVar, long j) throws IOException {
        return this.delegate.read(ckVar, j);
    }

    public lc timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }
}
