package j$.util.stream;

import j$.util.Objects;
import j$.util.U;
import java.util.function.Consumer;

final class R3 extends C0139m3 {
    R3(D0 d0, U u, boolean z) {
        super(d0, u, z);
    }

    R3(D0 d0, C0075a aVar, boolean z) {
        super(d0, aVar, z);
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        C0104f3 f3Var = new C0104f3();
        this.h = f3Var;
        Objects.requireNonNull(f3Var);
        this.e = this.b.J0(new Q3(f3Var, 0));
        this.f = new C0075a(this, 7);
    }

    /* access modifiers changed from: package-private */
    public final C0139m3 e(U u) {
        return new R3(this.b, u, this.a);
    }

    public final void forEachRemaining(Consumer consumer) {
        if (this.h != null || this.i) {
            do {
            } while (tryAdvance(consumer));
            return;
        }
        Objects.requireNonNull(consumer);
        c();
        Objects.requireNonNull(consumer);
        Q3 q3 = new Q3(consumer, 1);
        this.b.I0(this.d, q3);
        this.i = true;
    }

    public final boolean tryAdvance(Consumer consumer) {
        Object obj;
        Objects.requireNonNull(consumer);
        boolean a = a();
        if (a) {
            C0104f3 f3Var = (C0104f3) this.h;
            long j = this.g;
            if (f3Var.c == 0) {
                if (j < ((long) f3Var.b)) {
                    obj = f3Var.e[(int) j];
                } else {
                    throw new IndexOutOfBoundsException(Long.toString(j));
                }
            } else if (j < f3Var.count()) {
                int i = 0;
                while (i <= f3Var.c) {
                    long j2 = f3Var.d[i];
                    Object[] objArr = f3Var.f[i];
                    if (j < ((long) objArr.length) + j2) {
                        obj = objArr[(int) (j - j2)];
                    } else {
                        i++;
                    }
                }
                throw new IndexOutOfBoundsException(Long.toString(j));
            } else {
                throw new IndexOutOfBoundsException(Long.toString(j));
            }
            consumer.accept(obj);
        }
        return a;
    }
}
