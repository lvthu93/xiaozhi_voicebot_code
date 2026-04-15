package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.U;
import java.util.Comparator;
import java.util.function.Consumer;

final class f extends q implements U {
    final ConcurrentHashMap i;
    long j;

    f(l[] lVarArr, int i2, int i3, int i4, long j2, ConcurrentHashMap concurrentHashMap) {
        super(lVarArr, i2, i3, i4);
        this.i = concurrentHashMap;
        this.j = j2;
    }

    public final int characteristics() {
        return 4353;
    }

    public final long estimateSize() {
        return this.j;
    }

    public final void forEachRemaining(Consumer consumer) {
        consumer.getClass();
        while (true) {
            l a = a();
            if (a != null) {
                consumer.accept(new k(a.b, a.c, this.i));
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

    public final /* synthetic */ boolean hasCharacteristics(int i2) {
        return C0057b.e(this, i2);
    }

    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        l a = a();
        if (a == null) {
            return false;
        }
        consumer.accept(new k(a.b, a.c, this.i));
        return true;
    }

    public final U trySplit() {
        int i2 = this.f;
        int i3 = this.g;
        int i4 = (i2 + i3) >>> 1;
        if (i4 <= i2) {
            return null;
        }
        l[] lVarArr = this.a;
        int i5 = this.h;
        this.g = i4;
        long j2 = this.j >>> 1;
        this.j = j2;
        return new f(lVarArr, i5, i4, i3, j2, this.i);
    }
}
