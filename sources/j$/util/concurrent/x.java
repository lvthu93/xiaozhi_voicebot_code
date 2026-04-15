package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.H;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

final class x implements H {
    long a;
    final long b;
    final double c;
    final double d;

    x(long j, long j2, double d2, double d3) {
        this.a = j;
        this.b = j2;
        this.c = d2;
        this.d = d3;
    }

    /* renamed from: a */
    public final x trySplit() {
        long j = this.a;
        long j2 = (this.b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.a = j2;
        return new x(j, j2, this.c, this.d);
    }

    public final int characteristics() {
        return 17728;
    }

    public final long estimateSize() {
        return this.b - this.a;
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        long j = this.a;
        long j2 = this.b;
        if (j < j2) {
            this.a = j2;
            A b2 = A.b();
            do {
                doubleConsumer.accept(b2.d(this.c, this.d));
                j++;
            } while (j < j2);
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

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }

    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        long j = this.a;
        if (j >= this.b) {
            return false;
        }
        doubleConsumer.accept(A.b().d(this.c, this.d));
        this.a = j + 1;
        return true;
    }
}
