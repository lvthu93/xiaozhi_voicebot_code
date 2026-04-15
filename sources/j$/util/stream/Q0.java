package j$.util.stream;

import j$.util.C0058c;
import j$.util.Collection$EL;
import j$.util.Objects;
import j$.util.U;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class Q0 implements M0 {
    private final Collection a;

    Q0(Collection collection) {
        this.a = collection;
    }

    public final M0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final long count() {
        return (long) this.a.size();
    }

    public final void forEach(Consumer consumer) {
        Collection<Object> collection = this.a;
        if (collection instanceof C0058c) {
            ((C0058c) collection).forEach(consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        for (Object accept : collection) {
            consumer.accept(accept);
        }
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.S(this, j, j2, intFunction);
    }

    public final void j(Object[] objArr, int i) {
        for (Object obj : this.a) {
            objArr[i] = obj;
            i++;
        }
    }

    public final Object[] k(IntFunction intFunction) {
        Collection collection = this.a;
        return collection.toArray((Object[]) intFunction.apply(collection.size()));
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final U spliterator() {
        return Collection$EL.stream(this.a).spliterator();
    }

    public final String toString() {
        Collection collection = this.a;
        return String.format("CollectionNode[%d][%s]", new Object[]{Integer.valueOf(collection.size()), collection});
    }
}
