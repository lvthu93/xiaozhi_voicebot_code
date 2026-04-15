package j$.util.concurrent;

import j$.util.C0057b;
import j$.util.C0058c;
import j$.util.Collection$EL;
import j$.util.U;
import j$.util.stream.C0114h3;
import j$.util.stream.D0;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class i extends b implements Set, C0058c {
    private static final long serialVersionUID = 7249069246763182397L;

    i(ConcurrentHashMap concurrentHashMap) {
        super(concurrentHashMap);
    }

    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public final boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r2 = (java.util.Set) r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r2) {
        /*
            r1 = this;
            boolean r0 = r2 instanceof java.util.Set
            if (r0 == 0) goto L_0x0016
            java.util.Set r2 = (java.util.Set) r2
            if (r2 == r1) goto L_0x0014
            boolean r0 = r1.containsAll(r2)
            if (r0 == 0) goto L_0x0016
            boolean r2 = r2.containsAll(r1)
            if (r2 == 0) goto L_0x0016
        L_0x0014:
            r2 = 1
            goto L_0x0017
        L_0x0016:
            r2 = 0
        L_0x0017:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.i.equals(java.lang.Object):boolean");
    }

    public final void forEach(Consumer consumer) {
        consumer.getClass();
        l[] lVarArr = this.a.a;
        if (lVarArr != null) {
            q qVar = new q(lVarArr, lVarArr.length, 0, lVarArr.length);
            while (true) {
                l a = qVar.a();
                if (a != null) {
                    consumer.accept(a.b);
                } else {
                    return;
                }
            }
        }
    }

    public final int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (((C0059a) it).hasNext()) {
            i += ((h) it).next().hashCode();
        }
        return i;
    }

    public final Iterator iterator() {
        ConcurrentHashMap concurrentHashMap = this.a;
        l[] lVarArr = concurrentHashMap.a;
        int length = lVarArr == null ? 0 : lVarArr.length;
        return new h(lVarArr, length, length, concurrentHashMap, 0);
    }

    public final Stream parallelStream() {
        return C0114h3.k(D0.H0(Collection$EL.a(this), true));
    }

    public final boolean remove(Object obj) {
        return this.a.remove(obj) != null;
    }

    public final /* synthetic */ boolean removeIf(Predicate predicate) {
        return C0057b.f(this, predicate);
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
        return new j(lVarArr, length, 0, length, j, 0);
    }

    public final Object[] toArray(IntFunction intFunction) {
        return toArray((Object[]) intFunction.apply(0));
    }
}
