package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.Comparator;
import java.util.function.Consumer;

final class N3 extends P3 implements U, Consumer {
    Object f;

    N3(U u, long j, long j2) {
        super(u, j, j2);
    }

    N3(U u, N3 n3) {
        super(u, n3);
    }

    public final void accept(Object obj) {
        this.f = obj;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    /* access modifiers changed from: protected */
    public final U b(U u) {
        return new N3(u, this);
    }

    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        C0163r3 r3Var = null;
        while (true) {
            O3 f2 = f();
            if (f2 != O3.NO_MORE) {
                O3 o3 = O3.MAYBE_MORE;
                U u = this.a;
                if (f2 == o3) {
                    int i = this.c;
                    if (r3Var == null) {
                        r3Var = new C0163r3(i);
                    } else {
                        r3Var.a = 0;
                    }
                    long j = 0;
                    while (u.tryAdvance(r3Var)) {
                        j++;
                        if (j >= ((long) i)) {
                            break;
                        }
                    }
                    if (j != 0) {
                        long a = a(j);
                        for (int i2 = 0; ((long) i2) < a; i2++) {
                            consumer.accept(r3Var.b[i2]);
                        }
                    } else {
                        return;
                    }
                } else {
                    u.forEachRemaining(consumer);
                    return;
                }
            } else {
                return;
            }
        }
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

    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        while (f() != O3.NO_MORE && this.a.tryAdvance(this)) {
            if (a(1) == 1) {
                consumer.accept(this.f);
                this.f = null;
                return true;
            }
        }
        return false;
    }
}
