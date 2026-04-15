package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap;

public final class Interners {

    public static class InternerBuilder {
        public final MapMaker a = new MapMaker();
        public boolean b = true;

        public <E> Interner<E> build() {
            boolean z = this.b;
            MapMaker mapMaker = this.a;
            if (!z) {
                mapMaker.weakKeys();
            }
            return new InternerImpl(mapMaker);
        }

        public InternerBuilder concurrencyLevel(int i) {
            this.a.concurrencyLevel(i);
            return this;
        }

        public InternerBuilder strong() {
            this.b = true;
            return this;
        }

        public InternerBuilder weak() {
            this.b = false;
            return this;
        }
    }

    public static class InternerFunction<E> implements Function<E, E> {
        public final Interner<E> c;

        public InternerFunction(Interner<E> interner) {
            this.c = interner;
        }

        public E apply(E e) {
            return this.c.intern(e);
        }

        public boolean equals(Object obj) {
            if (obj instanceof InternerFunction) {
                return this.c.equals(((InternerFunction) obj).c);
            }
            return false;
        }

        public int hashCode() {
            return this.c.hashCode();
        }
    }

    public static final class InternerImpl<E> implements Interner<E> {
        public final MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> a;

        public InternerImpl(MapMaker mapMaker) {
            boolean z;
            MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> mapMakerInternalMap;
            Equivalence<Object> equals = Equivalence.equals();
            Equivalence<Object> equivalence = mapMaker.f;
            if (equivalence == null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "key equivalence was already set to %s", (Object) equivalence);
            mapMaker.f = (Equivalence) Preconditions.checkNotNull(equals);
            mapMaker.a = true;
            MapMakerInternalMap.AnonymousClass1 r0 = MapMakerInternalMap.n;
            MapMakerInternalMap.Strength a2 = mapMaker.a();
            MapMakerInternalMap.Strength.AnonymousClass1 r1 = MapMakerInternalMap.Strength.c;
            if (a2 == r1 && mapMaker.b() == r1) {
                mapMakerInternalMap = new MapMakerInternalMap<>(mapMaker, MapMakerInternalMap.StrongKeyDummyValueEntry.Helper.a);
            } else {
                MapMakerInternalMap.Strength a3 = mapMaker.a();
                MapMakerInternalMap.Strength.AnonymousClass2 r2 = MapMakerInternalMap.Strength.f;
                if (a3 == r2 && mapMaker.b() == r1) {
                    mapMakerInternalMap = new MapMakerInternalMap<>(mapMaker, MapMakerInternalMap.WeakKeyDummyValueEntry.Helper.a);
                } else if (mapMaker.b() == r2) {
                    throw new IllegalArgumentException("Map cannot have both weak and dummy values");
                } else {
                    throw new AssertionError();
                }
            }
            this.a = mapMakerInternalMap;
        }

        public E intern(E e) {
            MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> mapMakerInternalMap;
            MapMakerInternalMap.InternalEntry internalEntry;
            E key;
            do {
                mapMakerInternalMap = this.a;
                if (e == null) {
                    mapMakerInternalMap.getClass();
                    internalEntry = null;
                } else {
                    int b = mapMakerInternalMap.b(e);
                    internalEntry = mapMakerInternalMap.c(b).d(b, e);
                }
                if (internalEntry != null && (key = internalEntry.getKey()) != null) {
                    return key;
                }
            } while (mapMakerInternalMap.putIfAbsent(e, MapMaker.Dummy.c) != null);
            return e;
        }
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return newBuilder().strong().build();
    }

    public static <E> Interner<E> newWeakInterner() {
        return newBuilder().weak().build();
    }
}
