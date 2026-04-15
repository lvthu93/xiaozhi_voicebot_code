package j$.util.stream;

import java.util.HashSet;

final class r extends C0162r2 {
    HashSet b;

    r(C0182v2 v2Var) {
        super(v2Var);
    }

    public final void accept(Object obj) {
        if (!this.b.contains(obj)) {
            this.b.add(obj);
            this.a.accept(obj);
        }
    }

    public final void c(long j) {
        this.b = new HashSet();
        this.a.c(-1);
    }

    public final void end() {
        this.b = null;
        this.a.end();
    }
}
