package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import java.util.ArrayDeque;

/* renamed from: j$.util.stream.p1  reason: case insensitive filesystem */
abstract class C0152p1 extends C0161r1 implements Q {
    C0152p1(L0 l0) {
        super(l0);
    }

    public final void forEachRemaining(Object obj) {
        if (this.a != null) {
            if (this.d == null) {
                U u = this.c;
                if (u == null) {
                    ArrayDeque b = b();
                    while (true) {
                        L0 l0 = (L0) C0161r1.a(b);
                        if (l0 != null) {
                            l0.g(obj);
                        } else {
                            this.a = null;
                            return;
                        }
                    }
                } else {
                    ((Q) u).forEachRemaining(obj);
                }
            } else {
                do {
                } while (tryAdvance(obj));
            }
        }
    }

    public final boolean tryAdvance(Object obj) {
        L0 l0;
        if (!c()) {
            return false;
        }
        boolean tryAdvance = ((Q) this.d).tryAdvance(obj);
        if (!tryAdvance) {
            if (this.c != null || (l0 = (L0) C0161r1.a(this.e)) == null) {
                this.a = null;
            } else {
                Q spliterator = l0.spliterator();
                this.d = spliterator;
                return spliterator.tryAdvance(obj);
            }
        }
        return tryAdvance;
    }
}
