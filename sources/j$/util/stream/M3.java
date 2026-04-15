package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.Q;
import j$.util.U;
import java.util.Comparator;

abstract class M3 extends P3 implements Q {
    M3(Q q, long j, long j2) {
        super(q, j, j2);
    }

    M3(Q q, M3 m3) {
        super(q, m3);
    }

    public final void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
        C0159q3 q3Var = null;
        while (true) {
            O3 f = f();
            if (f != O3.NO_MORE) {
                O3 o3 = O3.MAYBE_MORE;
                U u = this.a;
                if (f == o3) {
                    int i = this.c;
                    if (q3Var == null) {
                        q3Var = i(i);
                    } else {
                        q3Var.b = 0;
                    }
                    long j = 0;
                    while (((Q) u).tryAdvance(q3Var)) {
                        j++;
                        if (j >= ((long) i)) {
                            break;
                        }
                    }
                    if (j != 0) {
                        q3Var.a(obj, a(j));
                    } else {
                        return;
                    }
                } else {
                    ((Q) u).forEachRemaining(obj);
                    return;
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void g(Object obj);

    public final Comparator getComparator() {
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    /* access modifiers changed from: protected */
    public abstract C0159q3 i(int i);

    public final boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        while (f() != O3.NO_MORE && ((Q) this.a).tryAdvance(this)) {
            if (a(1) == 1) {
                g(obj);
                return true;
            }
        }
        return false;
    }
}
