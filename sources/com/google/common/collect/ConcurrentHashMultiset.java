package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class ConcurrentHashMultiset<E> extends AbstractMultiset<E> implements Serializable {
    private static final long serialVersionUID = 1;
    public final transient ConcurrentMap<E, AtomicInteger> g;

    public static class FieldSettersHolder {
        public static final Serialization.FieldSetter<ConcurrentHashMultiset> a = Serialization.a(ConcurrentHashMultiset.class, "countMap");
    }

    public ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        Preconditions.checkArgument(concurrentMap.isEmpty(), "the backing map (%s) must be empty", (Object) concurrentMap);
        this.g = concurrentMap;
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset<>(new ConcurrentHashMap());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        FieldSettersHolder.a.a(this, (ConcurrentMap) objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.g);
    }

    public final Set<E> a() {
        final Set<E> keySet = this.g.keySet();
        return new ForwardingSet<E>() {
            public final Collection a() {
                return keySet;
            }

            public boolean contains(Object obj) {
                return obj != null && Collections2.d(obj, keySet);
            }

            public boolean containsAll(Collection<?> collection) {
                return Collections2.b(this, collection);
            }

            public final Object delegate() {
                return keySet;
            }

            public final Set<E> g() {
                return keySet;
            }

            public boolean remove(Object obj) {
                boolean z;
                if (obj == null) {
                    return false;
                }
                Set set = keySet;
                Preconditions.checkNotNull(set);
                try {
                    z = set.remove(obj);
                } catch (ClassCastException | NullPointerException unused) {
                    z = false;
                }
                if (z) {
                    return true;
                }
                return false;
            }

            public boolean removeAll(Collection<?> collection) {
                return Sets.c(this, (Collection) Preconditions.checkNotNull(collection));
            }
        };
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0062, code lost:
        r3 = new java.util.concurrent.atomic.AtomicInteger(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006b, code lost:
        if (r0.putIfAbsent(r11, r3) == null) goto L_0x0073;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int add(E r11, int r12) {
        /*
            r10 = this;
            com.google.common.base.Preconditions.checkNotNull(r11)
            if (r12 != 0) goto L_0x000a
            int r11 = r10.count(r11)
            return r11
        L_0x000a:
            java.lang.String r0 = "occurences"
            com.google.common.collect.CollectPreconditions.d(r12, r0)
        L_0x000f:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r0 = r10.g
            java.lang.Object r1 = com.google.common.collect.Maps.i(r0, r11)
            java.util.concurrent.atomic.AtomicInteger r1 = (java.util.concurrent.atomic.AtomicInteger) r1
            r2 = 0
            if (r1 != 0) goto L_0x0028
            java.util.concurrent.atomic.AtomicInteger r1 = new java.util.concurrent.atomic.AtomicInteger
            r1.<init>(r12)
            java.lang.Object r1 = r0.putIfAbsent(r11, r1)
            java.util.concurrent.atomic.AtomicInteger r1 = (java.util.concurrent.atomic.AtomicInteger) r1
            if (r1 != 0) goto L_0x0028
            return r2
        L_0x0028:
            int r3 = r1.get()
            if (r3 == 0) goto L_0x0062
            long r4 = (long) r3
            long r6 = (long) r12
            long r4 = r4 + r6
            int r6 = (int) r4
            long r7 = (long) r6
            int r9 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x0039
            r4 = 1
            goto L_0x003a
        L_0x0039:
            r4 = 0
        L_0x003a:
            java.lang.String r5 = "checkedAdd"
            defpackage.cg.d(r4, r5, r3, r12)     // Catch:{ ArithmeticException -> 0x0046 }
            boolean r4 = r1.compareAndSet(r3, r6)     // Catch:{ ArithmeticException -> 0x0046 }
            if (r4 == 0) goto L_0x0028
            return r3
        L_0x0046:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Overflow adding "
            r0.<init>(r1)
            r0.append(r12)
            java.lang.String r12 = " occurrences to a count of "
            r0.append(r12)
            r0.append(r3)
            java.lang.String r12 = r0.toString()
            r11.<init>(r12)
            throw r11
        L_0x0062:
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r12)
            java.lang.Object r4 = r0.putIfAbsent(r11, r3)
            if (r4 == 0) goto L_0x0073
            boolean r0 = r0.replace(r11, r1, r3)
            if (r0 == 0) goto L_0x000f
        L_0x0073:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.add(java.lang.Object, int):int");
    }

    public final int b() {
        return this.g.size();
    }

    public final Iterator<E> c() {
        throw new AssertionError("should never be called");
    }

    public void clear() {
        this.g.clear();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj) {
        return super.contains(obj);
    }

    public int count(Object obj) {
        AtomicInteger atomicInteger = (AtomicInteger) Maps.i(this.g, obj);
        if (atomicInteger == null) {
            return 0;
        }
        return atomicInteger.get();
    }

    @Deprecated
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public final Iterator<Multiset.Entry<E>> f() {
        final AnonymousClass2 r0 = new AbstractIterator<Multiset.Entry<E>>(this) {
            public final Iterator<Map.Entry<E, AtomicInteger>> g;

            {
                this.g = r1.g.entrySet().iterator();
            }

            public final Object computeNext() {
                Map.Entry next;
                int i;
                do {
                    Iterator<Map.Entry<E, AtomicInteger>> it = this.g;
                    if (!it.hasNext()) {
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                    next = it.next();
                    i = ((AtomicInteger) next.getValue()).get();
                } while (i == 0);
                return Multisets.immutableEntry(next.getKey(), i);
            }
        };
        return new ForwardingIterator<Multiset.Entry<E>>() {
            public Multiset.Entry<E> c;

            public final Iterator<Multiset.Entry<E>> a() {
                return r0;
            }

            public final Object delegate() {
                return r0;
            }

            public void remove() {
                boolean z;
                if (this.c != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                ConcurrentHashMultiset.this.setCount(this.c.getElement(), 0);
                this.c = null;
            }

            public Multiset.Entry<E> next() {
                Multiset.Entry<E> entry = (Multiset.Entry) super.next();
                this.c = entry;
                return entry;
            }
        };
    }

    public final ArrayList g() {
        ArrayList newArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
        for (Multiset.Entry entry : entrySet()) {
            Object element = entry.getElement();
            for (int count = entry.getCount(); count > 0; count--) {
                newArrayListWithExpectedSize.add(element);
            }
        }
        return newArrayListWithExpectedSize;
    }

    public boolean isEmpty() {
        return this.g.isEmpty();
    }

    public Iterator<E> iterator() {
        return Multisets.c(this);
    }

    public int remove(Object obj, int i) {
        int i2;
        int max;
        if (i == 0) {
            return count(obj);
        }
        CollectPreconditions.d(i, "occurences");
        ConcurrentMap concurrentMap = this.g;
        AtomicInteger atomicInteger = (AtomicInteger) Maps.i(concurrentMap, obj);
        if (atomicInteger == null) {
            return 0;
        }
        do {
            i2 = atomicInteger.get();
            if (i2 == 0) {
                return 0;
            }
            max = Math.max(0, i2 - i);
        } while (!atomicInteger.compareAndSet(i2, max));
        if (max == 0) {
            concurrentMap.remove(obj, atomicInteger);
        }
        return i2;
    }

    public boolean removeExactly(Object obj, int i) {
        int i2;
        int i3;
        if (i == 0) {
            return true;
        }
        CollectPreconditions.d(i, "occurences");
        ConcurrentMap concurrentMap = this.g;
        AtomicInteger atomicInteger = (AtomicInteger) Maps.i(concurrentMap, obj);
        if (atomicInteger == null) {
            return false;
        }
        do {
            i2 = atomicInteger.get();
            if (i2 < i) {
                return false;
            }
            i3 = i2 - i;
        } while (!atomicInteger.compareAndSet(i2, i3));
        if (i3 == 0) {
            concurrentMap.remove(obj, atomicInteger);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        if (r7 != 0) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002d, code lost:
        r3 = new java.util.concurrent.atomic.AtomicInteger(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        if (r0.putIfAbsent(r6, r3) == null) goto L_0x003e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setCount(E r6, int r7) {
        /*
            r5 = this;
            com.google.common.base.Preconditions.checkNotNull(r6)
            java.lang.String r0 = "count"
            com.google.common.collect.CollectPreconditions.b(r7, r0)
        L_0x0008:
            java.util.concurrent.ConcurrentMap<E, java.util.concurrent.atomic.AtomicInteger> r0 = r5.g
            java.lang.Object r1 = com.google.common.collect.Maps.i(r0, r6)
            java.util.concurrent.atomic.AtomicInteger r1 = (java.util.concurrent.atomic.AtomicInteger) r1
            r2 = 0
            if (r1 != 0) goto L_0x0024
            if (r7 != 0) goto L_0x0016
            return r2
        L_0x0016:
            java.util.concurrent.atomic.AtomicInteger r1 = new java.util.concurrent.atomic.AtomicInteger
            r1.<init>(r7)
            java.lang.Object r1 = r0.putIfAbsent(r6, r1)
            java.util.concurrent.atomic.AtomicInteger r1 = (java.util.concurrent.atomic.AtomicInteger) r1
            if (r1 != 0) goto L_0x0024
            return r2
        L_0x0024:
            int r3 = r1.get()
            if (r3 != 0) goto L_0x003f
            if (r7 != 0) goto L_0x002d
            return r2
        L_0x002d:
            java.util.concurrent.atomic.AtomicInteger r3 = new java.util.concurrent.atomic.AtomicInteger
            r3.<init>(r7)
            java.lang.Object r4 = r0.putIfAbsent(r6, r3)
            if (r4 == 0) goto L_0x003e
            boolean r0 = r0.replace(r6, r1, r3)
            if (r0 == 0) goto L_0x0008
        L_0x003e:
            return r2
        L_0x003f:
            boolean r4 = r1.compareAndSet(r3, r7)
            if (r4 == 0) goto L_0x0024
            if (r7 != 0) goto L_0x004a
            r0.remove(r6, r1)
        L_0x004a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ConcurrentHashMultiset.setCount(java.lang.Object, int):int");
    }

    public int size() {
        long j = 0;
        for (AtomicInteger atomicInteger : this.g.values()) {
            j += (long) atomicInteger.get();
        }
        return y3.b(j);
    }

    public Object[] toArray() {
        return g().toArray();
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> iterable) {
        ConcurrentHashMultiset<E> create = create();
        Iterables.addAll(create, iterable);
        return create;
    }

    public <T> T[] toArray(T[] tArr) {
        return g().toArray(tArr);
    }

    public class EntrySet extends AbstractMultiset<E>.EntrySet {
        public EntrySet() {
            super();
        }

        public final Multiset a() {
            return ConcurrentHashMultiset.this;
        }

        public Object[] toArray() {
            ArrayList newArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(newArrayListWithExpectedSize, iterator());
            return newArrayListWithExpectedSize.toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            ArrayList newArrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(size());
            Iterators.addAll(newArrayListWithExpectedSize, iterator());
            return newArrayListWithExpectedSize.toArray(tArr);
        }
    }

    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        return new ConcurrentHashMultiset<>(concurrentMap);
    }

    public boolean setCount(E e, int i, int i2) {
        Preconditions.checkNotNull(e);
        CollectPreconditions.b(i, "oldCount");
        CollectPreconditions.b(i2, "newCount");
        ConcurrentMap concurrentMap = this.g;
        AtomicInteger atomicInteger = (AtomicInteger) Maps.i(concurrentMap, e);
        if (atomicInteger != null) {
            int i3 = atomicInteger.get();
            if (i3 == i) {
                if (i3 == 0) {
                    if (i2 == 0) {
                        concurrentMap.remove(e, atomicInteger);
                        return true;
                    }
                    AtomicInteger atomicInteger2 = new AtomicInteger(i2);
                    if (concurrentMap.putIfAbsent(e, atomicInteger2) == null || concurrentMap.replace(e, atomicInteger, atomicInteger2)) {
                        return true;
                    }
                    return false;
                } else if (atomicInteger.compareAndSet(i3, i2)) {
                    if (i2 == 0) {
                        concurrentMap.remove(e, atomicInteger);
                    }
                    return true;
                }
            }
            return false;
        } else if (i != 0) {
            return false;
        } else {
            if (i2 == 0 || concurrentMap.putIfAbsent(e, new AtomicInteger(i2)) == null) {
                return true;
            }
            return false;
        }
    }
}
