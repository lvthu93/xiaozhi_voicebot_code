package j$.util.stream;

import j$.util.Objects;
import j$.util.U;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.s  reason: case insensitive filesystem */
final class C0164s extends C0133l2 {
    C0164s(C0085c cVar, int i) {
        super(cVar, i);
    }

    static Q0 c1(C0085c cVar, U u) {
        R0 r0 = new R0(21);
        R0 r02 = new R0(22);
        R0 r03 = new R0(23);
        Objects.requireNonNull(r0);
        Objects.requireNonNull(r02);
        Objects.requireNonNull(r03);
        return new Q0((Collection) new I1(C0134l3.REFERENCE, r03, r02, r0, 3).k(cVar, u));
    }

    /* access modifiers changed from: package-private */
    public final M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        if (C0129k3.DISTINCT.p(cVar.s0())) {
            return cVar.K0(u, false, intFunction);
        }
        if (C0129k3.ORDERED.p(cVar.s0())) {
            return c1(cVar, u);
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        C0150p pVar = new C0150p(0, atomicBoolean, concurrentHashMap);
        Objects.requireNonNull(pVar);
        new V(pVar, false).a(cVar, u);
        Set keySet = concurrentHashMap.keySet();
        if (atomicBoolean.get()) {
            HashSet hashSet = new HashSet(keySet);
            hashSet.add((Object) null);
            keySet = hashSet;
        }
        return new Q0(keySet);
    }

    /* access modifiers changed from: package-private */
    public final U U0(C0085c cVar, U u) {
        return C0129k3.DISTINCT.p(cVar.s0()) ? cVar.a1(u) : C0129k3.ORDERED.p(cVar.s0()) ? c1(cVar, u).spliterator() : new C0173t3(cVar.a1(u));
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        Objects.requireNonNull(v2Var);
        return C0129k3.DISTINCT.p(i) ? v2Var : C0129k3.SORTED.p(i) ? new C0155q(this, v2Var) : new r(v2Var);
    }
}
