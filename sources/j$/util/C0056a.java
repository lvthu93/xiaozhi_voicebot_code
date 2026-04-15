package j$.util;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;

/* renamed from: j$.util.a  reason: case insensitive filesystem */
final class C0056a implements U {
    private final List a;
    private int b;
    private int c;

    private C0056a(C0056a aVar, int i, int i2) {
        this.a = aVar.a;
        this.b = i;
        this.c = i2;
    }

    C0056a(List list) {
        this.a = list;
        this.b = 0;
        this.c = -1;
    }

    private int a() {
        int i = this.c;
        if (i >= 0) {
            return i;
        }
        int size = this.a.size();
        this.c = size;
        return size;
    }

    public final int characteristics() {
        return 16464;
    }

    public final long estimateSize() {
        return (long) (a() - this.b);
    }

    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        int a2 = a();
        int i = this.b;
        this.b = a2;
        while (i < a2) {
            try {
                consumer.accept(this.a.get(i));
                i++;
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
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
        consumer.getClass();
        int a2 = a();
        int i = this.b;
        if (i >= a2) {
            return false;
        }
        this.b = i + 1;
        try {
            consumer.accept(this.a.get(i));
            return true;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public final U trySplit() {
        int a2 = a();
        int i = this.b;
        int i2 = (a2 + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.b = i2;
        return new C0056a(this, i, i2);
    }
}
