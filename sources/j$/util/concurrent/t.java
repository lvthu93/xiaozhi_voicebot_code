package j$.util.concurrent;

import j$.util.C0058c;
import j$.util.Collection$EL;
import j$.util.U;
import j$.util.stream.C0114h3;
import j$.util.stream.D0;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

final class t extends b implements C0058c {
    private static final long serialVersionUID = 2249069246763182397L;

    t(ConcurrentHashMap concurrentHashMap) {
        super(concurrentHashMap);
    }

    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public final boolean contains(Object obj) {
        return this.a.containsValue(obj);
    }

    public final void forEach(Consumer consumer) {
        consumer.getClass();
        l[] lVarArr = this.a.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a = qVar.a();
                if (a != null) {
                    consumer.accept(a.c);
                } else {
                    return;
                }
            }
        }
    }

    public final Iterator iterator() {
        ConcurrentHashMap concurrentHashMap = this.a;
        l[] lVarArr = concurrentHashMap.a;
        int length = lVarArr == null ? 0 : lVarArr.length;
        return new h(lVarArr, length, length, concurrentHashMap, 1);
    }

    public final Stream parallelStream() {
        return C0114h3.k(D0.H0(Collection$EL.a(this), true));
    }

    public final boolean remove(Object obj) {
        C0059a aVar;
        if (obj == null) {
            return false;
        }
        Iterator it = iterator();
        do {
            aVar = (C0059a) it;
            if (!aVar.hasNext()) {
                return false;
            }
        } while (!obj.equals(((h) it).next()));
        aVar.remove();
        return true;
    }

    public final boolean removeAll(Collection collection) {
        collection.getClass();
        Iterator it = iterator();
        boolean z = false;
        while (true) {
            C0059a aVar = (C0059a) it;
            if (!aVar.hasNext()) {
                return z;
            }
            if (collection.contains(((h) it).next())) {
                aVar.remove();
                z = true;
            }
        }
    }

    public final boolean removeIf(Predicate predicate) {
        ConcurrentHashMap concurrentHashMap = this.a;
        concurrentHashMap.getClass();
        predicate.getClass();
        l[] lVarArr = concurrentHashMap.a;
        boolean z = false;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a = qVar.a();
                if (a == null) {
                    break;
                }
                Object obj = a.b;
                Object obj2 = a.c;
                if (predicate.test(obj2) && concurrentHashMap.h(obj, (Object) null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final U spliterator() {
        ConcurrentHashMap concurrentHashMap = this.a;
        long k = concurrentHashMap.k();
        l[] lVarArr = concurrentHashMap.a;
        int length = lVarArr == null ? 0 : lVarArr.length;
        long j = 0;
        if (k >= 0) {
            j = k;
        }
        return new j(lVarArr, length, 0, length, j, 1);
    }

    public final Object[] toArray(IntFunction intFunction) {
        return toArray((Object[]) intFunction.apply(0));
    }
}
