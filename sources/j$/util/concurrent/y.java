package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.K;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

final class y implements K {
    long a;
    final long b;
    final int c;
    final int d;

    y(long j, long j2, int i, int i2) {
        this.a = j;
        this.b = j2;
        this.c = i;
        this.d = i2;
    }

    /* renamed from: a */
    public final y trySplit() {
        long j = this.a;
        long j2 = (this.b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.a = j2;
        return new y(j, j2, this.c, this.d);
    }

    public final int characteristics() {
        return 17728;
    }

    public final long estimateSize() {
        return this.b - this.a;
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    public final void forEachRemaining(IntConsumer intConsumer) {
        intConsumer.getClass();
        long j = this.a;
        long j2 = this.b;
        if (j < j2) {
            this.a = j2;
            A b2 = A.b();
            do {
                intConsumer.accept(b2.e(this.c, this.d));
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
        return C0057b.i(this, consumer);
    }

    public final boolean tryAdvance(IntConsumer intConsumer) {
        intConsumer.getClass();
        long j = this.a;
        if (j >= this.b) {
            return false;
        }
        intConsumer.accept(A.b().e(this.c, this.d));
        this.a = j + 1;
        return true;
    }
}
