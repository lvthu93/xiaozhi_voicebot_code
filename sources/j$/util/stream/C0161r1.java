package j$.util.stream;

import j$.util.C0057b;
import j$.util.U;
import java.util.ArrayDeque;
import java.util.Comparator;

/* renamed from: j$.util.stream.r1  reason: case insensitive filesystem */
abstract class C0161r1 implements U {
    M0 a;
    int b;
    U c;
    U d;
    ArrayDeque e;

    C0161r1(M0 m0) {
        this.a = m0;
    }

    protected static M0 a(ArrayDeque arrayDeque) {
        while (true) {
            M0 m0 = (M0) arrayDeque.pollFirst();
            if (m0 != null) {
                if (m0.n() != 0) {
                    int n = m0.n();
                    while (true) {
                        n--;
                        if (n < 0) {
                            break;
                        }
                        arrayDeque.addFirst(m0.a(n));
                    }
                } else if (m0.count() > 0) {
                    return m0;
                }
            } else {
                return null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final ArrayDeque b() {
        ArrayDeque arrayDeque = new ArrayDeque(8);
        int n = this.a.n();
        while (true) {
            n--;
            if (n < this.b) {
                return arrayDeque;
            }
            arrayDeque.addFirst(this.a.a(n));
        }
    }

    /* access modifiers changed from: protected */
    public final boolean c() {
        if (this.a == null) {
            return false;
        }
        if (this.d != null) {
            return true;
        }
        U u = this.c;
        if (u == null) {
            ArrayDeque b2 = b();
            this.e = b2;
            M0 a2 = a(b2);
            if (a2 != null) {
                u = a2.spliterator();
            } else {
                this.a = null;
                return false;
            }
        }
        this.d = u;
        return true;
    }

    public final int characteristics() {
        return 64;
    }

    public final long estimateSize() {
        long j = 0;
        if (this.a == null) {
            return 0;
        }
        U u = this.c;
        if (u != null) {
            return u.estimateSize();
        }
        for (int i = this.b; i < this.a.n(); i++) {
            j += this.a.a(i).count();
        }
        return j;
    }

    public final Comparator getComparator() {
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final U trySplit() {
        M0 m0 = this.a;
        if (m0 == null || this.d != null) {
            return null;
        }
        U u = this.c;
        if (u != null) {
            return u.trySplit();
        }
        if (this.b < m0.n() - 1) {
            M0 m02 = this.a;
            int i = this.b;
            this.b = i + 1;
            return m02.a(i).spliterator();
        }
        M0 a2 = this.a.a(this.b);
        this.a = a2;
        if (a2.n() == 0) {
            U spliterator = this.a.spliterator();
            this.c = spliterator;
            return spliterator.trySplit();
        }
        M0 m03 = this.a;
        this.b = 0 + 1;
        return m03.a(0).spliterator();
    }
}
