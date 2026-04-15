package j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

final class a0 implements H {
    private final double[] a;
    private int b;
    private final int c;
    private final int d;

    public a0(double[] dArr, int i, int i2, int i3) {
        this.a = dArr;
        this.b = i;
        this.c = i2;
        this.d = i3 | 64 | 16384;
    }

    public final int characteristics() {
        return this.d;
    }

    public final long estimateSize() {
        return (long) (this.c - this.b);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        int i;
        doubleConsumer.getClass();
        double[] dArr = this.a;
        int length = dArr.length;
        int i2 = this.c;
        if (length >= i2 && (i = this.b) >= 0) {
            this.b = i2;
            if (i < i2) {
                do {
                    doubleConsumer.accept(dArr[i]);
                    i++;
                } while (i < i2);
            }
        }
    }

    public final Comparator getComparator() {
        if (C0057b.e(this, 4)) {
            return null;
        }
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
        int i = this.b;
        if (i < 0 || i >= this.c) {
            return false;
        }
        this.b = i + 1;
        doubleConsumer.accept(this.a[i]);
        return true;
    }

    public final H trySplit() {
        int i = this.b;
        int i2 = (this.c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.b = i2;
        return new a0(this.a, i, i2, this.d);
    }
}
