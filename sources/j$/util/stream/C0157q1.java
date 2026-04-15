package j$.util.stream;

import j$.util.U;
import java.util.ArrayDeque;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.q1  reason: case insensitive filesystem */
final class C0157q1 extends C0161r1 {
    C0157q1(M0 m0) {
        super(m0);
    }

    public final void forEachRemaining(Consumer consumer) {
        if (this.a != null) {
            if (this.d == null) {
                U u = this.c;
                if (u == null) {
                    ArrayDeque b = b();
                    while (true) {
                        M0 a = C0161r1.a(b);
                        if (a != null) {
                            a.forEach(consumer);
                        } else {
                            this.a = null;
                            return;
                        }
                    }
                } else {
                    u.forEachRemaining(consumer);
                }
            } else {
                do {
                } while (tryAdvance(consumer));
            }
        }
    }

    public final boolean tryAdvance(Consumer consumer) {
        M0 a;
        if (!c()) {
            return false;
        }
        boolean tryAdvance = this.d.tryAdvance(consumer);
        if (!tryAdvance) {
            if (this.c != null || (a = C0161r1.a(this.e)) == null) {
                this.a = null;
            } else {
                U spliterator = a.spliterator();
                this.d = spliterator;
                return spliterator.tryAdvance(consumer);
            }
        }
        return tryAdvance;
    }
}
