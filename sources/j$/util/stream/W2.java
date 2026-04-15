package j$.util.stream;

import j$.util.C0057b;
import j$.util.Objects;
import j$.util.U;
import j$.util.i0;
import java.util.Comparator;
import java.util.function.Consumer;

final class W2 implements U {
    int a;
    final int b;
    int c;
    final int d;
    Object[] e;
    final /* synthetic */ C0104f3 f;

    W2(C0104f3 f3Var, int i, int i2, int i3, int i4) {
        this.f = f3Var;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        Object[][] objArr = f3Var.f;
        this.e = objArr == null ? f3Var.e : objArr[i];
    }

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

    public final void forEachRemaining(Consumer consumer) {
        C0104f3 f3Var;
        Objects.requireNonNull(consumer);
        int i = this.a;
        int i2 = this.d;
        int i3 = this.b;
        if (i < i3 || (i == i3 && this.c < i2)) {
            int i4 = this.c;
            while (true) {
                f3Var = this.f;
                if (i >= i3) {
                    break;
                }
                Object[] objArr = f3Var.f[i];
                while (i4 < objArr.length) {
                    consumer.accept(objArr[i4]);
                    i4++;
                }
                i++;
                i4 = 0;
            }
            Object[] objArr2 = this.a == i3 ? this.e : f3Var.f[i3];
            while (i4 < i2) {
                consumer.accept(objArr2[i4]);
                i4++;
            }
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

    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        int i = this.a;
        int i2 = this.b;
        if (i >= i2 && (i != i2 || this.c >= this.d)) {
            return false;
        }
        Object[] objArr = this.e;
        int i3 = this.c;
        this.c = i3 + 1;
        consumer.accept(objArr[i3]);
        if (this.c == this.e.length) {
            this.c = 0;
            int i4 = this.a + 1;
            this.a = i4;
            Object[][] objArr2 = this.f.f;
            if (objArr2 != null && i4 <= i2) {
                this.e = objArr2[i4];
            }
        }
        return true;
    }

    public final U trySplit() {
        int i = this.a;
        int i2 = this.b;
        if (i < i2) {
            int i3 = i2 - 1;
            int i4 = this.c;
            C0104f3 f3Var = this.f;
            W2 w2 = new W2(f3Var, i, i3, i4, f3Var.f[i3].length);
            this.a = i2;
            this.c = 0;
            this.e = f3Var.f[i2];
            return w2;
        } else if (i != i2) {
            return null;
        } else {
            int i5 = this.c;
            int i6 = (this.d - i5) / 2;
            if (i6 == 0) {
                return null;
            }
            U m = i0.m(this.e, i5, i5 + i6);
            this.c += i6;
            return m;
        }
    }
}
