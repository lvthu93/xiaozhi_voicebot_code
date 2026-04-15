package defpackage;

import java.io.IOException;

/* renamed from: e3  reason: default package */
public abstract class e3 implements za {
    private final za delegate;

    public e3(za zaVar) {
        if (zaVar != null) {
            this.delegate = zaVar;
            return;
        }
        throw new IllegalArgumentException("delegate == null");
    }

    public void close() throws IOException {
        this.delegate.close();
    }

    public final za delegate() {
        return this.delegate;
    }

    public void flush() throws IOException {
        this.delegate.flush();
    }

    public lc timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    public void write(ck ckVar, long j) throws IOException {
        this.delegate.write(ckVar, j);
    }
}
