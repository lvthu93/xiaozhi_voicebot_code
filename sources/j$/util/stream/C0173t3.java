package j$.util.stream;

import j$.util.C0057b;
import j$.util.U;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.Consumer$CC;
import java.util.Comparator;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.t3  reason: case insensitive filesystem */
final class C0173t3 implements U, Consumer {
    private static final Object d = new Object();
    private final U a;
    private final ConcurrentHashMap b;
    private Object c;

    C0173t3(U u) {
        this(u, new ConcurrentHashMap());
    }

    private C0173t3(U u, ConcurrentHashMap concurrentHashMap) {
        this.a = u;
        this.b = concurrentHashMap;
    }

    /* access modifiers changed from: package-private */
    public final void a(Consumer consumer, Object obj) {
        if (this.b.putIfAbsent(obj != null ? obj : d, Boolean.TRUE) == null) {
            consumer.accept(obj);
        }
    }

    public final void accept(Object obj) {
        this.c = obj;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final int characteristics() {
        return (this.a.characteristics() & -16469) | 1;
    }

    public final long estimateSize() {
        return this.a.estimateSize();
    }

    public final void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(new C0150p(6, this, consumer));
    }

    public final Comparator getComparator() {
        return this.a.getComparator();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final boolean tryAdvance(Consumer consumer) {
        while (this.a.tryAdvance(this)) {
            Object obj = this.c;
            if (obj == null) {
                obj = d;
            }
            if (this.b.putIfAbsent(obj, Boolean.TRUE) == null) {
                consumer.accept(this.c);
                this.c = null;
                return true;
            }
        }
        return false;
    }

    public final U trySplit() {
        U trySplit = this.a.trySplit();
        if (trySplit != null) {
            return new C0173t3(trySplit, this.b);
        }
        return null;
    }
}
