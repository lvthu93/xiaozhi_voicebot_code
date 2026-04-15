package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.N;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

final class z implements N {
    long a;
    final long b;
    final long c;
    final long d;

    z(long j, long j2, long j3, long j4) {
        this.a = j;
        this.b = j2;
        this.c = j3;
        this.d = j4;
    }

    /* renamed from: a */
    public final z trySplit() {
        long j = this.a;
        long j2 = (this.b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.a = j2;
        return new z(j, j2, this.c, this.d);
    }

    public final int characteristics() {
        return 17728;
    }

    public final long estimateSize() {
        return this.b - this.a;
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    public final void forEachRemaining(LongConsumer longConsumer) {
        longConsumer.getClass();
        long j = this.a;
        long j2 = this.b;
        if (j < j2) {
            this.a = j2;
            A b2 = A.b();
            do {
                longConsumer.accept(b2.f(this.c, this.d));
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
        return C0057b.j(this, consumer);
    }

    public final boolean tryAdvance(LongConsumer longConsumer) {
        longConsumer.getClass();
        long j = this.a;
        if (j >= this.b) {
            return false;
        }
        longConsumer.accept(A.b().f(this.c, this.d));
        this.a = j + 1;
        return true;
    }
}
