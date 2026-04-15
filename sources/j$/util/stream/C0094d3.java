package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.Q;
import java.util.Comparator;

/* renamed from: j$.util.stream.d3  reason: case insensitive filesystem */
abstract class C0094d3 implements Q {
    int a;
    final int b;
    int c;
    final int d;
    Object e;
    final /* synthetic */ C0099e3 f;

    C0094d3(C0099e3 e3Var, int i, int i2, int i3, int i4) {
        this.f = e3Var;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        Object[] objArr = e3Var.f;
        this.e = objArr == null ? e3Var.e : objArr[i];
    }

    /* access modifiers changed from: package-private */
    public abstract void a(int i, Object obj, Object obj2);

    /* access modifiers changed from: package-private */
    public abstract Q b(Object obj, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract Q c(int i, int i2, int i3, int i4);

    public final int characteristics() {
        return 16464;
    }

    public final long estimateSize() {
        int i = this.a;
        int i2 = this.d;
        int i3 = this.b;
        if (i == i3) {
            return ((long) i2) - ((long) this.c);
        }
        long[] jArr = this.f.d;
        return ((jArr[i3] + ((long) i2)) - jArr[i]) - ((long) this.c);
    }

    public final void forEachRemaining(Object obj) {
        C0099e3 e3Var;
        Objects.requireNonNull(obj);
        int i = this.a;
        int i2 = this.d;
        int i3 = this.b;
        if (i < i3 || (i == i3 && this.c < i2)) {
            int i4 = this.c;
            while (true) {
                e3Var = this.f;
                if (i >= i3) {
                    break;
                }
                Object obj2 = e3Var.f[i];
                e3Var.o(obj2, i4, e3Var.p(obj2), obj);
                i++;
                i4 = 0;
            }
            e3Var.o(this.a == i3 ? this.e : e3Var.f[i3], i4, i2, obj);
            this.a = i3;
            this.c = i2;
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

    public final boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        int i = this.a;
        int i2 = this.b;
        if (i >= i2 && (i != i2 || this.c >= this.d)) {
            return false;
        }
        Object obj2 = this.e;
        int i3 = this.c;
        this.c = i3 + 1;
        a(i3, obj2, obj);
        int i4 = this.c;
        Object obj3 = this.e;
        C0099e3 e3Var = this.f;
        if (i4 == e3Var.p(obj3)) {
            this.c = 0;
            int i5 = this.a + 1;
            this.a = i5;
            Object[] objArr = e3Var.f;
            if (objArr != null && i5 <= i2) {
                this.e = objArr[i5];
            }
        }
        return true;
    }

    public final Q trySplit() {
        int i = this.a;
        int i2 = this.b;
        if (i < i2) {
            int i3 = this.c;
            C0099e3 e3Var = this.f;
            Q c2 = c(i, i2 - 1, i3, e3Var.p(e3Var.f[i2 - 1]));
            this.a = i2;
            this.c = 0;
            this.e = e3Var.f[i2];
            return c2;
        } else if (i != i2) {
            return null;
        } else {
            int i4 = this.c;
            int i5 = (this.d - i4) / 2;
            if (i5 == 0) {
                return null;
            }
            Q b2 = b(this.e, i4, i5);
            this.c += i5;
            return b2;
        }
    }
}
