package com.google.common.collect;

import com.google.common.base.Equivalence;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.google.common.collect.MapMakerInternalMap.InternalEntry;
import com.google.common.collect.MapMakerInternalMap.Segment;
import j$.util.Iterator;
import j$.util.concurrent.ConcurrentMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class MapMakerInternalMap<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable, j$.util.concurrent.ConcurrentMap {
    public static final AnonymousClass1 n = new WeakValueReference<Object, Object, DummyInternalEntry>() {
        public void clear() {
        }

        public WeakValueReference<Object, Object, DummyInternalEntry> copyFor(ReferenceQueue<Object> referenceQueue, DummyInternalEntry dummyInternalEntry) {
            return this;
        }

        public Object get() {
            return null;
        }

        public DummyInternalEntry getEntry() {
            return null;
        }
    };
    private static final long serialVersionUID = 5;
    public final transient int c;
    public final transient int f;
    public final transient Segment<K, V, E, S>[] g;
    public final int h;
    public final Equivalence<Object> i;
    public final transient InternalEntryHelper<K, V, E, S> j;
    public transient Set<K> k;
    public transient Collection<V> l;
    public transient Set<Map.Entry<K, V>> m;

    public static abstract class AbstractSerializationProxy<K, V> extends ForwardingConcurrentMap<K, V> implements Serializable {
        private static final long serialVersionUID = 3;
        public final Strength c;
        public final Strength f;
        public final Equivalence<Object> g;
        public final int h;
        public transient ConcurrentMap<K, V> i;

        public AbstractSerializationProxy(Strength strength, Strength strength2, Equivalence equivalence, int i2, ConcurrentMap concurrentMap) {
            this.c = strength;
            this.f = strength2;
            this.g = equivalence;
            this.h = i2;
            this.i = concurrentMap;
        }

        public final Map a() {
            return this.i;
        }

        public final ConcurrentMap<K, V> c() {
            return this.i;
        }

        public final Object delegate() {
            return this.i;
        }
    }

    public static abstract class AbstractStrongKeyEntry<K, V, E extends InternalEntry<K, V, E>> implements InternalEntry<K, V, E> {
        public final K a;
        public final int b;
        public final E c;

        public AbstractStrongKeyEntry(K k, int i, E e) {
            this.a = k;
            this.b = i;
            this.c = e;
        }

        public int getHash() {
            return this.b;
        }

        public K getKey() {
            return this.a;
        }

        public E getNext() {
            return this.c;
        }
    }

    public static abstract class AbstractWeakKeyEntry<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<K> implements InternalEntry<K, V, E> {
        public final int a;
        public final E b;

        public AbstractWeakKeyEntry(ReferenceQueue<K> referenceQueue, K k, int i, E e) {
            super(k, referenceQueue);
            this.a = i;
            this.b = e;
        }

        public int getHash() {
            return this.a;
        }

        public K getKey() {
            return get();
        }

        public E getNext() {
            return this.b;
        }
    }

    public static final class CleanupMapTask implements Runnable {
        public final WeakReference<MapMakerInternalMap<?, ?, ?, ?>> c;

        public CleanupMapTask(MapMakerInternalMap<?, ?, ?, ?> mapMakerInternalMap) {
            this.c = new WeakReference<>(mapMakerInternalMap);
        }

        public void run() {
            MapMakerInternalMap mapMakerInternalMap = this.c.get();
            if (mapMakerInternalMap != null) {
                for (Segment<K, V, E, S> j : mapMakerInternalMap.g) {
                    j.j();
                }
                return;
            }
            throw new CancellationException();
        }
    }

    public static final class DummyInternalEntry implements InternalEntry<Object, Object, DummyInternalEntry> {
        public DummyInternalEntry() {
            throw new AssertionError();
        }

        public int getHash() {
            throw new AssertionError();
        }

        public Object getKey() {
            throw new AssertionError();
        }

        public Object getValue() {
            throw new AssertionError();
        }

        public DummyInternalEntry getNext() {
            throw new AssertionError();
        }
    }

    public final class EntryIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<Map.Entry<K, V>> {
        public EntryIterator(MapMakerInternalMap mapMakerInternalMap) {
            super();
        }

        public Map.Entry<K, V> next() {
            return d();
        }
    }

    public final class EntrySet extends SafeToArraySet<Map.Entry<K, V>> {
        public EntrySet() {
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r4 = (java.util.Map.Entry) r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x000f, code lost:
            r2 = r3.c;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean contains(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                java.lang.Object r0 = r4.getKey()
                if (r0 != 0) goto L_0x000f
                return r1
            L_0x000f:
                com.google.common.collect.MapMakerInternalMap r2 = com.google.common.collect.MapMakerInternalMap.this
                java.lang.Object r0 = r2.get(r0)
                if (r0 == 0) goto L_0x002c
                com.google.common.collect.MapMakerInternalMap$InternalEntryHelper<K, V, E, S> r2 = r2.j
                com.google.common.collect.MapMakerInternalMap$Strength r2 = r2.valueStrength()
                com.google.common.base.Equivalence r2 = r2.b()
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r2.equivalent(r4, r0)
                if (r4 == 0) goto L_0x002c
                r1 = 1
            L_0x002c:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.EntrySet.contains(java.lang.Object):boolean");
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(MapMakerInternalMap.this);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r4 = (java.util.Map.Entry) r4;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean remove(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                java.lang.Object r0 = r4.getKey()
                if (r0 == 0) goto L_0x001b
                com.google.common.collect.MapMakerInternalMap r2 = com.google.common.collect.MapMakerInternalMap.this
                java.lang.Object r4 = r4.getValue()
                boolean r4 = r2.remove(r0, r4)
                if (r4 == 0) goto L_0x001b
                r1 = 1
            L_0x001b:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.EntrySet.remove(java.lang.Object):boolean");
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    public abstract class HashIterator<T> implements Iterator<T>, j$.util.Iterator {
        public int c;
        public int f = -1;
        public Segment<K, V, E, S> g;
        public AtomicReferenceArray<E> h;
        public E i;
        public MapMakerInternalMap<K, V, E, S>.WriteThroughEntry j;
        public MapMakerInternalMap<K, V, E, S>.WriteThroughEntry k;

        public HashIterator() {
            this.c = MapMakerInternalMap.this.g.length - 1;
            a();
        }

        public final void a() {
            boolean z;
            this.j = null;
            E e = this.i;
            if (e != null) {
                while (true) {
                    E next = e.getNext();
                    this.i = next;
                    if (next == null) {
                        break;
                    } else if (c(next)) {
                        z = true;
                        break;
                    } else {
                        e = this.i;
                    }
                }
            }
            z = false;
            if (!z && !e()) {
                while (true) {
                    int i2 = this.c;
                    if (i2 >= 0) {
                        Segment<K, V, E, S>[] segmentArr = MapMakerInternalMap.this.g;
                        this.c = i2 - 1;
                        Segment<K, V, E, S> segment = segmentArr[i2];
                        this.g = segment;
                        if (segment.f != 0) {
                            AtomicReferenceArray<E> atomicReferenceArray = this.g.i;
                            this.h = atomicReferenceArray;
                            this.f = atomicReferenceArray.length() - 1;
                            if (e()) {
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* JADX INFO: finally extract failed */
        public final boolean c(E e) {
            MapMakerInternalMap mapMakerInternalMap = MapMakerInternalMap.this;
            try {
                Object key = e.getKey();
                mapMakerInternalMap.getClass();
                Object obj = null;
                if (e.getKey() != null) {
                    Object value = e.getValue();
                    if (value != null) {
                        obj = value;
                    }
                }
                if (obj != null) {
                    this.j = new WriteThroughEntry(key, obj);
                    this.g.g();
                    return true;
                }
                this.g.g();
                return false;
            } catch (Throwable th) {
                this.g.g();
                throw th;
            }
        }

        public final MapMakerInternalMap<K, V, E, S>.WriteThroughEntry d() {
            MapMakerInternalMap<K, V, E, S>.WriteThroughEntry writeThroughEntry = this.j;
            if (writeThroughEntry != null) {
                this.k = writeThroughEntry;
                a();
                return this.k;
            }
            throw new NoSuchElementException();
        }

        public final boolean e() {
            while (true) {
                int i2 = this.f;
                boolean z = false;
                if (i2 < 0) {
                    return false;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.h;
                this.f = i2 - 1;
                E e = (InternalEntry) atomicReferenceArray.get(i2);
                this.i = e;
                if (e != null) {
                    if (c(e)) {
                        break;
                    }
                    E e2 = this.i;
                    if (e2 != null) {
                        while (true) {
                            E next = e2.getNext();
                            this.i = next;
                            if (next == null) {
                                break;
                            } else if (c(next)) {
                                z = true;
                                break;
                            } else {
                                e2 = this.i;
                            }
                        }
                    }
                    if (z) {
                        break;
                    }
                }
            }
            return true;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.j != null;
        }

        public abstract T next();

        public void remove() {
            boolean z;
            if (this.k != null) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.e(z);
            MapMakerInternalMap.this.remove(this.k.getKey());
            this.k = null;
        }
    }

    public interface InternalEntry<K, V, E extends InternalEntry<K, V, E>> {
        int getHash();

        K getKey();

        E getNext();

        V getValue();
    }

    public interface InternalEntryHelper<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> {
        E copy(S s, E e, E e2);

        Strength keyStrength();

        E newEntry(S s, K k, int i, E e);

        S newSegment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i, int i2);

        void setValue(S s, E e, V v);

        Strength valueStrength();
    }

    public final class KeyIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<K> {
        public KeyIterator(MapMakerInternalMap mapMakerInternalMap) {
            super();
        }

        public K next() {
            return d().getKey();
        }
    }

    public final class KeySet extends SafeToArraySet<K> {
        public KeySet() {
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsKey(obj);
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public java.util.Iterator<K> iterator() {
            return new KeyIterator(MapMakerInternalMap.this);
        }

        public boolean remove(Object obj) {
            return MapMakerInternalMap.this.remove(obj) != null;
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }
    }

    public static abstract class SafeToArraySet<E> extends AbstractSet<E> {
        public Object[] toArray() {
            return MapMakerInternalMap.a(this).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return MapMakerInternalMap.a(this).toArray(tArr);
        }
    }

    public static abstract class Segment<K, V, E extends InternalEntry<K, V, E>, S extends Segment<K, V, E, S>> extends ReentrantLock {
        public static final /* synthetic */ int k = 0;
        public final MapMakerInternalMap<K, V, E, S> c;
        public volatile int f;
        public int g;
        public int h;
        public volatile AtomicReferenceArray<E> i;
        public final AtomicInteger j = new AtomicInteger();

        public Segment(MapMakerInternalMap<K, V, E, S> mapMakerInternalMap, int i2, int i3) {
            this.c = mapMakerInternalMap;
            AtomicReferenceArray<E> atomicReferenceArray = new AtomicReferenceArray<>(i2);
            int length = (atomicReferenceArray.length() * 3) / 4;
            this.h = length;
            if (length == i3) {
                this.h = length + 1;
            }
            this.i = atomicReferenceArray;
        }

        /* JADX INFO: finally extract failed */
        public final void a(ReferenceQueue<K> referenceQueue) {
            int i2 = 0;
            do {
                Reference<? extends K> poll = referenceQueue.poll();
                if (poll != null) {
                    InternalEntry internalEntry = (InternalEntry) poll;
                    MapMakerInternalMap<K, V, E, S> mapMakerInternalMap = this.c;
                    mapMakerInternalMap.getClass();
                    int hash = internalEntry.getHash();
                    Segment<K, V, E, S> c2 = mapMakerInternalMap.c(hash);
                    c2.lock();
                    try {
                        AtomicReferenceArray<E> atomicReferenceArray = c2.i;
                        int length = hash & (atomicReferenceArray.length() - 1);
                        InternalEntry internalEntry2 = (InternalEntry) atomicReferenceArray.get(length);
                        InternalEntry internalEntry3 = internalEntry2;
                        while (true) {
                            if (internalEntry3 == null) {
                                break;
                            } else if (internalEntry3 == internalEntry) {
                                c2.g++;
                                atomicReferenceArray.set(length, c2.i(internalEntry2, internalEntry3));
                                c2.f--;
                                break;
                            } else {
                                internalEntry3 = internalEntry3.getNext();
                            }
                        }
                        c2.unlock();
                        i2++;
                    } catch (Throwable th) {
                        c2.unlock();
                        throw th;
                    }
                } else {
                    return;
                }
            } while (i2 != 16);
        }

        /* JADX INFO: finally extract failed */
        public final void b(ReferenceQueue<V> referenceQueue) {
            int i2 = 0;
            do {
                Reference<? extends V> poll = referenceQueue.poll();
                if (poll != null) {
                    WeakValueReference weakValueReference = (WeakValueReference) poll;
                    MapMakerInternalMap<K, V, E, S> mapMakerInternalMap = this.c;
                    mapMakerInternalMap.getClass();
                    InternalEntry entry = weakValueReference.getEntry();
                    int hash = entry.getHash();
                    Segment<K, V, E, S> c2 = mapMakerInternalMap.c(hash);
                    Object key = entry.getKey();
                    c2.lock();
                    try {
                        AtomicReferenceArray<E> atomicReferenceArray = c2.i;
                        int length = (atomicReferenceArray.length() - 1) & hash;
                        InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                        InternalEntry internalEntry2 = internalEntry;
                        while (true) {
                            if (internalEntry2 == null) {
                                break;
                            }
                            Object key2 = internalEntry2.getKey();
                            if (internalEntry2.getHash() != hash || key2 == null || !c2.c.i.equivalent(key, key2)) {
                                internalEntry2 = internalEntry2.getNext();
                            } else if (((WeakValueEntry) internalEntry2).getValueReference() == weakValueReference) {
                                c2.g++;
                                atomicReferenceArray.set(length, c2.i(internalEntry, internalEntry2));
                                c2.f--;
                            }
                        }
                        c2.unlock();
                        i2++;
                    } catch (Throwable th) {
                        c2.unlock();
                        throw th;
                    }
                } else {
                    return;
                }
            } while (i2 != 16);
        }

        public final void c() {
            AtomicReferenceArray<E> atomicReferenceArray = this.i;
            int length = atomicReferenceArray.length();
            if (length < 1073741824) {
                int i2 = this.f;
                AtomicReferenceArray<E> atomicReferenceArray2 = new AtomicReferenceArray<>(length << 1);
                this.h = (atomicReferenceArray2.length() * 3) / 4;
                int length2 = atomicReferenceArray2.length() - 1;
                for (int i3 = 0; i3 < length; i3++) {
                    InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(i3);
                    if (internalEntry != null) {
                        InternalEntry next = internalEntry.getNext();
                        int hash = internalEntry.getHash() & length2;
                        if (next == null) {
                            atomicReferenceArray2.set(hash, internalEntry);
                        } else {
                            InternalEntry internalEntry2 = internalEntry;
                            while (next != null) {
                                int hash2 = next.getHash() & length2;
                                if (hash2 != hash) {
                                    internalEntry2 = next;
                                    hash = hash2;
                                }
                                next = next.getNext();
                            }
                            atomicReferenceArray2.set(hash, internalEntry2);
                            while (internalEntry != internalEntry2) {
                                int hash3 = internalEntry.getHash() & length2;
                                E copy = this.c.j.copy(k(), internalEntry, (InternalEntry) atomicReferenceArray2.get(hash3));
                                if (copy != null) {
                                    atomicReferenceArray2.set(hash3, copy);
                                } else {
                                    i2--;
                                }
                                internalEntry = internalEntry.getNext();
                            }
                        }
                    }
                }
                this.i = atomicReferenceArray2;
                this.f = i2;
            }
        }

        public final InternalEntry d(int i2, Object obj) {
            if (this.f == 0) {
                return null;
            }
            AtomicReferenceArray<E> atomicReferenceArray = this.i;
            for (InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get((atomicReferenceArray.length() - 1) & i2); internalEntry != null; internalEntry = internalEntry.getNext()) {
                if (internalEntry.getHash() == i2) {
                    Object key = internalEntry.getKey();
                    if (key == null) {
                        m();
                    } else if (this.c.i.equivalent(obj, key)) {
                        return internalEntry;
                    }
                }
            }
            return null;
        }

        public void e() {
        }

        public void f() {
        }

        public final void g() {
            if ((this.j.incrementAndGet() & 63) == 0) {
                j();
            }
        }

        public final V h(K k2, int i2, V v, boolean z) {
            lock();
            try {
                j();
                int i3 = this.f + 1;
                if (i3 > this.h) {
                    c();
                    i3 = this.f + 1;
                }
                AtomicReferenceArray<E> atomicReferenceArray = this.i;
                int length = (atomicReferenceArray.length() - 1) & i2;
                InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
                InternalEntry internalEntry2 = internalEntry;
                while (internalEntry2 != null) {
                    Object key = internalEntry2.getKey();
                    if (internalEntry2.getHash() != i2 || key == null || !this.c.i.equivalent(k2, key)) {
                        internalEntry2 = internalEntry2.getNext();
                    } else {
                        V value = internalEntry2.getValue();
                        if (value == null) {
                            this.g++;
                            l(internalEntry2, v);
                            this.f = this.f;
                            return null;
                        } else if (z) {
                            unlock();
                            return value;
                        } else {
                            this.g++;
                            l(internalEntry2, v);
                            unlock();
                            return value;
                        }
                    }
                }
                this.g++;
                E newEntry = this.c.j.newEntry(k(), k2, i2, internalEntry);
                l(newEntry, v);
                atomicReferenceArray.set(length, newEntry);
                this.f = i3;
                unlock();
                return null;
            } finally {
                unlock();
            }
        }

        public final E i(E e, E e2) {
            int i2 = this.f;
            E next = e2.getNext();
            while (e != e2) {
                E copy = this.c.j.copy(k(), e, next);
                if (copy != null) {
                    next = copy;
                } else {
                    i2--;
                }
                e = e.getNext();
            }
            this.f = i2;
            return next;
        }

        public final void j() {
            if (tryLock()) {
                try {
                    f();
                    this.j.set(0);
                } finally {
                    unlock();
                }
            }
        }

        public abstract S k();

        public final void l(E e, V v) {
            this.c.j.setValue(k(), e, v);
        }

        public final void m() {
            if (tryLock()) {
                try {
                    f();
                } finally {
                    unlock();
                }
            }
        }
    }

    public static final class SerializationProxy<K, V> extends AbstractSerializationProxy<K, V> {
        private static final long serialVersionUID = 3;

        public SerializationProxy(Strength strength, Strength strength2, Equivalence equivalence, int i, ConcurrentMap concurrentMap) {
            super(strength, strength2, equivalence, i, concurrentMap);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            boolean z;
            boolean z2;
            objectInputStream.defaultReadObject();
            MapMaker initialCapacity = new MapMaker().initialCapacity(objectInputStream.readInt());
            Strength strength = initialCapacity.d;
            boolean z3 = false;
            if (strength == null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "Key strength was already set to %s", (Object) strength);
            Strength strength2 = this.c;
            initialCapacity.d = (Strength) Preconditions.checkNotNull(strength2);
            Strength.AnonymousClass1 r4 = Strength.c;
            if (strength2 != r4) {
                initialCapacity.a = true;
            }
            Strength strength3 = initialCapacity.e;
            if (strength3 == null) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkState(z2, "Value strength was already set to %s", (Object) strength3);
            Strength strength4 = this.f;
            initialCapacity.e = (Strength) Preconditions.checkNotNull(strength4);
            if (strength4 != r4) {
                initialCapacity.a = true;
            }
            Equivalence<Object> equivalence = initialCapacity.f;
            if (equivalence == null) {
                z3 = true;
            }
            Preconditions.checkState(z3, "key equivalence was already set to %s", (Object) equivalence);
            initialCapacity.f = (Equivalence) Preconditions.checkNotNull(this.g);
            initialCapacity.a = true;
            this.i = initialCapacity.concurrencyLevel(this.h).makeMap();
            while (true) {
                Object readObject = objectInputStream.readObject();
                if (readObject != null) {
                    this.i.put(readObject, objectInputStream.readObject());
                } else {
                    return;
                }
            }
        }

        private Object readResolve() {
            return this.i;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.i.size());
            for (Map.Entry next : this.i.entrySet()) {
                objectOutputStream.writeObject(next.getKey());
                objectOutputStream.writeObject(next.getValue());
            }
            objectOutputStream.writeObject((Object) null);
        }
    }

    public enum Strength {
        ;

        /* access modifiers changed from: public */
        Strength() {
            throw null;
        }

        public abstract Equivalence<Object> b();
    }

    public static final class StrongKeyDummyValueEntry<K> extends AbstractStrongKeyEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>> implements StrongValueEntry<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>> {

        public static final class Helper<K> implements InternalEntryHelper<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
            public static final Helper<?> a = new Helper<>();

            public StrongKeyDummyValueEntry<K> copy(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry2) {
                return new StrongKeyDummyValueEntry<>(strongKeyDummyValueEntry.a, strongKeyDummyValueEntry.b, strongKeyDummyValueEntry2);
            }

            public Strength keyStrength() {
                return Strength.c;
            }

            public void setValue(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            public Strength valueStrength() {
                return Strength.c;
            }

            public StrongKeyDummyValueEntry<K> newEntry(StrongKeyDummyValueSegment<K> strongKeyDummyValueSegment, K k, int i, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
                return new StrongKeyDummyValueEntry<>(k, i, strongKeyDummyValueEntry);
            }

            public StrongKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int i, int i2) {
                return new StrongKeyDummyValueSegment<>(mapMakerInternalMap, i, i2);
            }
        }

        public StrongKeyDummyValueEntry(K k, int i, StrongKeyDummyValueEntry<K> strongKeyDummyValueEntry) {
            super(k, i, strongKeyDummyValueEntry);
        }

        public MapMaker.Dummy getValue() {
            return MapMaker.Dummy.c;
        }
    }

    public static final class StrongKeyDummyValueSegment<K> extends Segment<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> {
        public StrongKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, StrongKeyDummyValueEntry<K>, StrongKeyDummyValueSegment<K>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final Segment k() {
            return this;
        }

        public StrongKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (StrongKeyDummyValueEntry) internalEntry;
        }
    }

    public static final class StrongKeyStrongValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, StrongKeyStrongValueEntry<K, V>> {
        public volatile V d = null;

        public static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
            public static final Helper<?, ?> a = new Helper<>();

            public StrongKeyStrongValueEntry<K, V> copy(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry2) {
                StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry3 = new StrongKeyStrongValueEntry<>(strongKeyStrongValueEntry.a, strongKeyStrongValueEntry.b, strongKeyStrongValueEntry2);
                strongKeyStrongValueEntry3.d = strongKeyStrongValueEntry.d;
                return strongKeyStrongValueEntry3;
            }

            public Strength keyStrength() {
                return Strength.c;
            }

            public void setValue(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry, V v) {
                strongKeyStrongValueEntry.d = v;
            }

            public Strength valueStrength() {
                return Strength.c;
            }

            public StrongKeyStrongValueEntry<K, V> newEntry(StrongKeyStrongValueSegment<K, V> strongKeyStrongValueSegment, K k, int i, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
                return new StrongKeyStrongValueEntry<>(k, i, strongKeyStrongValueEntry);
            }

            public StrongKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new StrongKeyStrongValueSegment<>(mapMakerInternalMap, i, i2);
            }
        }

        public StrongKeyStrongValueEntry(K k, int i, StrongKeyStrongValueEntry<K, V> strongKeyStrongValueEntry) {
            super(k, i, strongKeyStrongValueEntry);
        }

        public V getValue() {
            return this.d;
        }
    }

    public static final class StrongKeyStrongValueSegment<K, V> extends Segment<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> {
        public StrongKeyStrongValueSegment(MapMakerInternalMap<K, V, StrongKeyStrongValueEntry<K, V>, StrongKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final Segment k() {
            return this;
        }

        public StrongKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyStrongValueEntry) internalEntry;
        }
    }

    public static final class StrongKeyWeakValueEntry<K, V> extends AbstractStrongKeyEntry<K, V, StrongKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, StrongKeyWeakValueEntry<K, V>> {
        public volatile WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> d = MapMakerInternalMap.n;

        public static final class Helper<K, V> implements InternalEntryHelper<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
            public static final Helper<?, ?> a = new Helper<>();

            public Strength keyStrength() {
                return Strength.c;
            }

            public Strength valueStrength() {
                return Strength.f;
            }

            public StrongKeyWeakValueEntry<K, V> copy(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry2) {
                int i = Segment.k;
                if (strongKeyWeakValueEntry.getValue() == null) {
                    return null;
                }
                ReferenceQueue<V> referenceQueue = strongKeyWeakValueSegment.l;
                StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry3 = new StrongKeyWeakValueEntry<>(strongKeyWeakValueEntry.a, strongKeyWeakValueEntry.b, strongKeyWeakValueEntry2);
                strongKeyWeakValueEntry3.d = strongKeyWeakValueEntry.d.copyFor(referenceQueue, strongKeyWeakValueEntry3);
                return strongKeyWeakValueEntry3;
            }

            public StrongKeyWeakValueEntry<K, V> newEntry(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, K k, int i, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
                return new StrongKeyWeakValueEntry<>(k, i, strongKeyWeakValueEntry);
            }

            public StrongKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new StrongKeyWeakValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public void setValue(StrongKeyWeakValueSegment<K, V> strongKeyWeakValueSegment, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry, V v) {
                ReferenceQueue<V> referenceQueue = strongKeyWeakValueSegment.l;
                WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> weakValueReference = strongKeyWeakValueEntry.d;
                strongKeyWeakValueEntry.d = new WeakValueReferenceImpl(referenceQueue, v, strongKeyWeakValueEntry);
                weakValueReference.clear();
            }
        }

        public StrongKeyWeakValueEntry(K k, int i, StrongKeyWeakValueEntry<K, V> strongKeyWeakValueEntry) {
            super(k, i, strongKeyWeakValueEntry);
        }

        public void clearValue() {
            this.d.clear();
        }

        public V getValue() {
            return this.d.get();
        }

        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getValueReference() {
            return this.d;
        }
    }

    public static final class StrongKeyWeakValueSegment<K, V> extends Segment<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> {
        public final ReferenceQueue<V> l = new ReferenceQueue<>();

        public StrongKeyWeakValueSegment(MapMakerInternalMap<K, V, StrongKeyWeakValueEntry<K, V>, StrongKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final void e() {
            do {
            } while (this.l.poll() != null);
        }

        public final void f() {
            b(this.l);
        }

        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return castForTesting((InternalEntry) internalEntry).getValueReference();
        }

        public final Segment k() {
            return this;
        }

        public WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.l, v, castForTesting((InternalEntry) internalEntry));
        }

        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            StrongKeyWeakValueEntry castForTesting = castForTesting((InternalEntry) internalEntry);
            WeakValueReference<K, V, StrongKeyWeakValueEntry<K, V>> weakValueReference2 = castForTesting.d;
            castForTesting.d = weakValueReference;
            weakValueReference2.clear();
        }

        public StrongKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (StrongKeyWeakValueEntry) internalEntry;
        }
    }

    public interface StrongValueEntry<K, V, E extends InternalEntry<K, V, E>> extends InternalEntry<K, V, E> {
    }

    public final class ValueIterator extends MapMakerInternalMap<K, V, E, S>.HashIterator<V> {
        public ValueIterator(MapMakerInternalMap mapMakerInternalMap) {
            super();
        }

        public V next() {
            return d().getValue();
        }
    }

    public final class Values extends AbstractCollection<V> {
        public Values() {
        }

        public void clear() {
            MapMakerInternalMap.this.clear();
        }

        public boolean contains(Object obj) {
            return MapMakerInternalMap.this.containsValue(obj);
        }

        public boolean isEmpty() {
            return MapMakerInternalMap.this.isEmpty();
        }

        public java.util.Iterator<V> iterator() {
            return new ValueIterator(MapMakerInternalMap.this);
        }

        public int size() {
            return MapMakerInternalMap.this.size();
        }

        public Object[] toArray() {
            return MapMakerInternalMap.a(this).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return MapMakerInternalMap.a(this).toArray(tArr);
        }
    }

    public static final class WeakKeyDummyValueEntry<K> extends AbstractWeakKeyEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>> implements StrongValueEntry<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>> {

        public static final class Helper<K> implements InternalEntryHelper<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
            public static final Helper<?> a = new Helper<>();

            public Strength keyStrength() {
                return Strength.f;
            }

            public void setValue(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, MapMaker.Dummy dummy) {
            }

            public Strength valueStrength() {
                return Strength.c;
            }

            public WeakKeyDummyValueEntry<K> copy(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry2) {
                if (weakKeyDummyValueEntry.getKey() == null) {
                    return null;
                }
                return new WeakKeyDummyValueEntry<>(weakKeyDummyValueSegment.l, weakKeyDummyValueEntry.getKey(), weakKeyDummyValueEntry.a, weakKeyDummyValueEntry2);
            }

            public WeakKeyDummyValueEntry<K> newEntry(WeakKeyDummyValueSegment<K> weakKeyDummyValueSegment, K k, int i, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
                return new WeakKeyDummyValueEntry<>(weakKeyDummyValueSegment.l, k, i, weakKeyDummyValueEntry);
            }

            public WeakKeyDummyValueSegment<K> newSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int i, int i2) {
                return new WeakKeyDummyValueSegment<>(mapMakerInternalMap, i, i2);
            }
        }

        public WeakKeyDummyValueEntry(ReferenceQueue<K> referenceQueue, K k, int i, WeakKeyDummyValueEntry<K> weakKeyDummyValueEntry) {
            super(referenceQueue, k, i, weakKeyDummyValueEntry);
        }

        public MapMaker.Dummy getValue() {
            return MapMaker.Dummy.c;
        }
    }

    public static final class WeakKeyDummyValueSegment<K> extends Segment<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> {
        public final ReferenceQueue<K> l = new ReferenceQueue<>();

        public WeakKeyDummyValueSegment(MapMakerInternalMap<K, MapMaker.Dummy, WeakKeyDummyValueEntry<K>, WeakKeyDummyValueSegment<K>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final void e() {
            do {
            } while (this.l.poll() != null);
        }

        public final void f() {
            a(this.l);
        }

        public final Segment k() {
            return this;
        }

        public WeakKeyDummyValueEntry<K> castForTesting(InternalEntry<K, MapMaker.Dummy, ?> internalEntry) {
            return (WeakKeyDummyValueEntry) internalEntry;
        }
    }

    public static final class WeakKeyStrongValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyStrongValueEntry<K, V>> implements StrongValueEntry<K, V, WeakKeyStrongValueEntry<K, V>> {
        public volatile V c = null;

        public static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
            public static final Helper<?, ?> a = new Helper<>();

            public Strength keyStrength() {
                return Strength.f;
            }

            public void setValue(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, V v) {
                weakKeyStrongValueEntry.c = v;
            }

            public Strength valueStrength() {
                return Strength.c;
            }

            public WeakKeyStrongValueEntry<K, V> copy(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry2) {
                if (weakKeyStrongValueEntry.getKey() == null) {
                    return null;
                }
                WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry3 = new WeakKeyStrongValueEntry<>(weakKeyStrongValueSegment.l, weakKeyStrongValueEntry.getKey(), weakKeyStrongValueEntry.a, weakKeyStrongValueEntry2);
                weakKeyStrongValueEntry3.c = weakKeyStrongValueEntry.c;
                return weakKeyStrongValueEntry3;
            }

            public WeakKeyStrongValueEntry<K, V> newEntry(WeakKeyStrongValueSegment<K, V> weakKeyStrongValueSegment, K k, int i, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
                return new WeakKeyStrongValueEntry<>(weakKeyStrongValueSegment.l, k, i, weakKeyStrongValueEntry);
            }

            public WeakKeyStrongValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new WeakKeyStrongValueSegment<>(mapMakerInternalMap, i, i2);
            }
        }

        public WeakKeyStrongValueEntry(ReferenceQueue<K> referenceQueue, K k, int i, WeakKeyStrongValueEntry<K, V> weakKeyStrongValueEntry) {
            super(referenceQueue, k, i, weakKeyStrongValueEntry);
        }

        public V getValue() {
            return this.c;
        }
    }

    public static final class WeakKeyStrongValueSegment<K, V> extends Segment<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> {
        public final ReferenceQueue<K> l = new ReferenceQueue<>();

        public WeakKeyStrongValueSegment(MapMakerInternalMap<K, V, WeakKeyStrongValueEntry<K, V>, WeakKeyStrongValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final void e() {
            do {
            } while (this.l.poll() != null);
        }

        public final void f() {
            a(this.l);
        }

        public final Segment k() {
            return this;
        }

        public WeakKeyStrongValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyStrongValueEntry) internalEntry;
        }
    }

    public static final class WeakKeyWeakValueEntry<K, V> extends AbstractWeakKeyEntry<K, V, WeakKeyWeakValueEntry<K, V>> implements WeakValueEntry<K, V, WeakKeyWeakValueEntry<K, V>> {
        public volatile WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> c = MapMakerInternalMap.n;

        public static final class Helper<K, V> implements InternalEntryHelper<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
            public static final Helper<?, ?> a = new Helper<>();

            public Strength keyStrength() {
                return Strength.f;
            }

            public Strength valueStrength() {
                return Strength.f;
            }

            public WeakKeyWeakValueEntry<K, V> copy(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry2) {
                if (weakKeyWeakValueEntry.getKey() == null) {
                    return null;
                }
                int i = Segment.k;
                if (weakKeyWeakValueEntry.getValue() == null) {
                    return null;
                }
                ReferenceQueue<K> referenceQueue = weakKeyWeakValueSegment.l;
                ReferenceQueue<V> referenceQueue2 = weakKeyWeakValueSegment.m;
                WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry3 = new WeakKeyWeakValueEntry<>(referenceQueue, weakKeyWeakValueEntry.getKey(), weakKeyWeakValueEntry.a, weakKeyWeakValueEntry2);
                weakKeyWeakValueEntry3.c = weakKeyWeakValueEntry.c.copyFor(referenceQueue2, weakKeyWeakValueEntry3);
                return weakKeyWeakValueEntry3;
            }

            public WeakKeyWeakValueEntry<K, V> newEntry(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, K k, int i, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
                return new WeakKeyWeakValueEntry<>(weakKeyWeakValueSegment.l, k, i, weakKeyWeakValueEntry);
            }

            public WeakKeyWeakValueSegment<K, V> newSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
                return new WeakKeyWeakValueSegment<>(mapMakerInternalMap, i, i2);
            }

            public void setValue(WeakKeyWeakValueSegment<K, V> weakKeyWeakValueSegment, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry, V v) {
                ReferenceQueue<V> referenceQueue = weakKeyWeakValueSegment.m;
                WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> weakValueReference = weakKeyWeakValueEntry.c;
                weakKeyWeakValueEntry.c = new WeakValueReferenceImpl(referenceQueue, v, weakKeyWeakValueEntry);
                weakValueReference.clear();
            }
        }

        public WeakKeyWeakValueEntry(ReferenceQueue<K> referenceQueue, K k, int i, WeakKeyWeakValueEntry<K, V> weakKeyWeakValueEntry) {
            super(referenceQueue, k, i, weakKeyWeakValueEntry);
        }

        public void clearValue() {
            this.c.clear();
        }

        public V getValue() {
            return this.c.get();
        }

        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getValueReference() {
            return this.c;
        }
    }

    public static final class WeakKeyWeakValueSegment<K, V> extends Segment<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> {
        public final ReferenceQueue<K> l = new ReferenceQueue<>();
        public final ReferenceQueue<V> m = new ReferenceQueue<>();

        public WeakKeyWeakValueSegment(MapMakerInternalMap<K, V, WeakKeyWeakValueEntry<K, V>, WeakKeyWeakValueSegment<K, V>> mapMakerInternalMap, int i, int i2) {
            super(mapMakerInternalMap, i, i2);
        }

        public final void e() {
            do {
            } while (this.l.poll() != null);
        }

        public final void f() {
            a(this.l);
            b(this.m);
        }

        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> getWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry) {
            return castForTesting((InternalEntry) internalEntry).getValueReference();
        }

        public final Segment k() {
            return this;
        }

        public WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> newWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, V v) {
            return new WeakValueReferenceImpl(this.m, v, castForTesting((InternalEntry) internalEntry));
        }

        public void setWeakValueReferenceForTesting(InternalEntry<K, V, ?> internalEntry, WeakValueReference<K, V, ? extends InternalEntry<K, V, ?>> weakValueReference) {
            WeakKeyWeakValueEntry castForTesting = castForTesting((InternalEntry) internalEntry);
            WeakValueReference<K, V, WeakKeyWeakValueEntry<K, V>> weakValueReference2 = castForTesting.c;
            castForTesting.c = weakValueReference;
            weakValueReference2.clear();
        }

        public WeakKeyWeakValueEntry<K, V> castForTesting(InternalEntry<K, V, ?> internalEntry) {
            return (WeakKeyWeakValueEntry) internalEntry;
        }
    }

    public interface WeakValueEntry<K, V, E extends InternalEntry<K, V, E>> extends InternalEntry<K, V, E> {
        void clearValue();

        WeakValueReference<K, V, E> getValueReference();
    }

    public interface WeakValueReference<K, V, E extends InternalEntry<K, V, E>> {
        void clear();

        WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e);

        V get();

        E getEntry();
    }

    public static final class WeakValueReferenceImpl<K, V, E extends InternalEntry<K, V, E>> extends WeakReference<V> implements WeakValueReference<K, V, E> {
        public final E a;

        public WeakValueReferenceImpl(ReferenceQueue<V> referenceQueue, V v, E e) {
            super(v, referenceQueue);
            this.a = e;
        }

        public WeakValueReference<K, V, E> copyFor(ReferenceQueue<V> referenceQueue, E e) {
            return new WeakValueReferenceImpl(referenceQueue, get(), e);
        }

        public E getEntry() {
            return this.a;
        }
    }

    public final class WriteThroughEntry extends AbstractMapEntry<K, V> {
        public final K c;
        public V f;

        public WriteThroughEntry(K k, V v) {
            this.c = k;
            this.f = v;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            if (!this.c.equals(entry.getKey()) || !this.f.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        public K getKey() {
            return this.c;
        }

        public V getValue() {
            return this.f;
        }

        public int hashCode() {
            return this.c.hashCode() ^ this.f.hashCode();
        }

        public V setValue(V v) {
            V put = MapMakerInternalMap.this.put(this.c, v);
            this.f = v;
            return put;
        }
    }

    public MapMakerInternalMap(MapMaker mapMaker, InternalEntryHelper<K, V, E, S> internalEntryHelper) {
        int i2 = mapMaker.c;
        this.h = Math.min(i2 == -1 ? 4 : i2, 65536);
        this.i = (Equivalence) MoreObjects.firstNonNull(mapMaker.f, mapMaker.a().b());
        this.j = internalEntryHelper;
        int i3 = mapMaker.b;
        int min = Math.min(i3 == -1 ? 16 : i3, 1073741824);
        int i4 = 0;
        int i5 = 1;
        int i6 = 1;
        int i7 = 0;
        while (i6 < this.h) {
            i7++;
            i6 <<= 1;
        }
        this.f = 32 - i7;
        this.c = i6 - 1;
        this.g = new Segment[i6];
        int i8 = min / i6;
        while (i5 < (i6 * i8 < min ? i8 + 1 : i8)) {
            i5 <<= 1;
        }
        while (true) {
            Segment<K, V, E, S>[] segmentArr = this.g;
            if (i4 < segmentArr.length) {
                segmentArr[i4] = this.j.newSegment(this, i5, -1);
                i4++;
            } else {
                return;
            }
        }
    }

    public static ArrayList a(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterators.addAll(arrayList, collection.iterator());
        return arrayList;
    }

    public final int b(Object obj) {
        int hash = this.i.hash(obj);
        int i2 = hash + ((hash << 15) ^ -12931);
        int i3 = i2 ^ (i2 >>> 10);
        int i4 = i3 + (i3 << 3);
        int i5 = i4 ^ (i4 >>> 6);
        int i6 = (i5 << 2) + (i5 << 14) + i5;
        return (i6 >>> 16) ^ i6;
    }

    public final Segment<K, V, E, S> c(int i2) {
        return this.g[(i2 >>> this.f) & this.c];
    }

    public void clear() {
        Segment<K, V, E, S>[] segmentArr = this.g;
        int length = segmentArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            Segment<K, V, E, S> segment = segmentArr[i2];
            if (segment.f != 0) {
                segment.lock();
                try {
                    AtomicReferenceArray<E> atomicReferenceArray = segment.i;
                    for (int i3 = 0; i3 < atomicReferenceArray.length(); i3++) {
                        atomicReferenceArray.set(i3, (Object) null);
                    }
                    segment.e();
                    segment.j.set(0);
                    segment.g++;
                    segment.f = 0;
                } finally {
                    segment.unlock();
                }
            }
        }
    }

    public final /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$compute(this, obj, biFunction);
    }

    public final /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        return ConcurrentMap.CC.$default$computeIfAbsent(this, obj, function);
    }

    public final /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$computeIfPresent(this, obj, biFunction);
    }

    public boolean containsKey(Object obj) {
        InternalEntry d;
        boolean z = false;
        if (obj == null) {
            return false;
        }
        int b = b(obj);
        Segment c2 = c(b);
        c2.getClass();
        try {
            if (!(c2.f == 0 || (d = c2.d(b, obj)) == null || d.getValue() == null)) {
                z = true;
            }
            return z;
        } finally {
            c2.g();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0053 A[LOOP:3: B:12:0x0029->B:24:0x0053, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0051 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean containsValue(java.lang.Object r17) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = 0
            if (r1 != 0) goto L_0x0008
            return r2
        L_0x0008:
            com.google.common.collect.MapMakerInternalMap$Segment<K, V, E, S>[] r3 = r0.g
            r4 = -1
            r6 = 0
        L_0x000d:
            r7 = 3
            if (r6 >= r7) goto L_0x006f
            int r7 = r3.length
            r8 = 0
            r10 = 0
        L_0x0014:
            if (r10 >= r7) goto L_0x0065
            r11 = r3[r10]
            int r12 = r11.f
            java.util.concurrent.atomic.AtomicReferenceArray<E> r12 = r11.i
            r13 = 0
        L_0x001d:
            int r14 = r12.length()
            if (r13 >= r14) goto L_0x005d
            java.lang.Object r14 = r12.get(r13)
            com.google.common.collect.MapMakerInternalMap$InternalEntry r14 = (com.google.common.collect.MapMakerInternalMap.InternalEntry) r14
        L_0x0029:
            if (r14 == 0) goto L_0x0059
            java.lang.Object r15 = r14.getKey()
            if (r15 != 0) goto L_0x0035
            r11.m()
            goto L_0x003e
        L_0x0035:
            java.lang.Object r15 = r14.getValue()
            if (r15 != 0) goto L_0x003f
            r11.m()
        L_0x003e:
            r15 = 0
        L_0x003f:
            if (r15 == 0) goto L_0x0053
            com.google.common.collect.MapMakerInternalMap$InternalEntryHelper<K, V, E, S> r2 = r0.j
            com.google.common.collect.MapMakerInternalMap$Strength r2 = r2.valueStrength()
            com.google.common.base.Equivalence r2 = r2.b()
            boolean r2 = r2.equivalent(r1, r15)
            if (r2 == 0) goto L_0x0053
            r1 = 1
            return r1
        L_0x0053:
            com.google.common.collect.MapMakerInternalMap$InternalEntry r14 = r14.getNext()
            r2 = 0
            goto L_0x0029
        L_0x0059:
            int r13 = r13 + 1
            r2 = 0
            goto L_0x001d
        L_0x005d:
            int r2 = r11.g
            long r11 = (long) r2
            long r8 = r8 + r11
            int r10 = r10 + 1
            r2 = 0
            goto L_0x0014
        L_0x0065:
            int r2 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x006a
            goto L_0x006f
        L_0x006a:
            int r6 = r6 + 1
            r4 = r8
            r2 = 0
            goto L_0x000d
        L_0x006f:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.containsValue(java.lang.Object):boolean");
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.m;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.m = entrySet;
        return entrySet;
    }

    public final /* synthetic */ void forEach(BiConsumer biConsumer) {
        ConcurrentMap.CC.$default$forEach(this, biConsumer);
    }

    public V get(Object obj) {
        V v = null;
        if (obj == null) {
            return null;
        }
        int b = b(obj);
        Segment c2 = c(b);
        c2.getClass();
        try {
            InternalEntry d = c2.d(b, obj);
            if (d != null) {
                v = d.getValue();
                if (v == null) {
                    c2.m();
                }
            }
            return v;
        } finally {
            c2.g();
        }
    }

    public final /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return ConcurrentMap.CC.$default$getOrDefault(this, obj, obj2);
    }

    public boolean isEmpty() {
        Segment<K, V, E, S>[] segmentArr = this.g;
        long j2 = 0;
        for (int i2 = 0; i2 < segmentArr.length; i2++) {
            if (segmentArr[i2].f != 0) {
                return false;
            }
            j2 += (long) segmentArr[i2].g;
        }
        if (j2 == 0) {
            return true;
        }
        for (int i3 = 0; i3 < segmentArr.length; i3++) {
            if (segmentArr[i3].f != 0) {
                return false;
            }
            j2 -= (long) segmentArr[i3].g;
        }
        if (j2 != 0) {
            return false;
        }
        return true;
    }

    public Set<K> keySet() {
        Set<K> set = this.k;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.k = keySet;
        return keySet;
    }

    public final /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$merge(this, obj, obj2, biFunction);
    }

    public V put(K k2, V v) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v);
        int b = b(k2);
        return c(b).h(k2, b, v, false);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public V putIfAbsent(K k2, V v) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v);
        int b = b(k2);
        return c(b).h(k2, b, v, true);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004a, code lost:
        if ((r7.getValue() == null) != false) goto L_0x004c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V remove(java.lang.Object r11) {
        /*
            r10 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = r10.b(r11)
            com.google.common.collect.MapMakerInternalMap$Segment r2 = r10.c(r1)
            r2.lock()
            r2.j()     // Catch:{ all -> 0x006b }
            java.util.concurrent.atomic.AtomicReferenceArray<E> r3 = r2.i     // Catch:{ all -> 0x006b }
            int r4 = r3.length()     // Catch:{ all -> 0x006b }
            r5 = 1
            int r4 = r4 - r5
            r4 = r4 & r1
            java.lang.Object r6 = r3.get(r4)     // Catch:{ all -> 0x006b }
            com.google.common.collect.MapMakerInternalMap$InternalEntry r6 = (com.google.common.collect.MapMakerInternalMap.InternalEntry) r6     // Catch:{ all -> 0x006b }
            r7 = r6
        L_0x0022:
            if (r7 == 0) goto L_0x0067
            java.lang.Object r8 = r7.getKey()     // Catch:{ all -> 0x006b }
            int r9 = r7.getHash()     // Catch:{ all -> 0x006b }
            if (r9 != r1) goto L_0x0062
            if (r8 == 0) goto L_0x0062
            com.google.common.collect.MapMakerInternalMap<K, V, E, S> r9 = r2.c     // Catch:{ all -> 0x006b }
            com.google.common.base.Equivalence<java.lang.Object> r9 = r9.i     // Catch:{ all -> 0x006b }
            boolean r8 = r9.equivalent(r11, r8)     // Catch:{ all -> 0x006b }
            if (r8 == 0) goto L_0x0062
            java.lang.Object r11 = r7.getValue()     // Catch:{ all -> 0x006b }
            if (r11 == 0) goto L_0x0041
            goto L_0x004c
        L_0x0041:
            java.lang.Object r1 = r7.getValue()     // Catch:{ all -> 0x006b }
            if (r1 != 0) goto L_0x0049
            r1 = 1
            goto L_0x004a
        L_0x0049:
            r1 = 0
        L_0x004a:
            if (r1 == 0) goto L_0x0067
        L_0x004c:
            int r0 = r2.g     // Catch:{ all -> 0x006b }
            int r0 = r0 + r5
            r2.g = r0     // Catch:{ all -> 0x006b }
            com.google.common.collect.MapMakerInternalMap$InternalEntry r0 = r2.i(r6, r7)     // Catch:{ all -> 0x006b }
            int r1 = r2.f     // Catch:{ all -> 0x006b }
            int r1 = r1 - r5
            r3.set(r4, r0)     // Catch:{ all -> 0x006b }
            r2.f = r1     // Catch:{ all -> 0x006b }
            r2.unlock()
            r0 = r11
            goto L_0x006a
        L_0x0062:
            com.google.common.collect.MapMakerInternalMap$InternalEntry r7 = r7.getNext()     // Catch:{ all -> 0x006b }
            goto L_0x0022
        L_0x0067:
            r2.unlock()
        L_0x006a:
            return r0
        L_0x006b:
            r11 = move-exception
            r2.unlock()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.remove(java.lang.Object):java.lang.Object");
    }

    public boolean replace(K k2, V v, V v2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v2);
        if (v == null) {
            return false;
        }
        int b = b(k2);
        Segment c2 = c(b);
        c2.lock();
        try {
            c2.j();
            AtomicReferenceArray<E> atomicReferenceArray = c2.i;
            int length = (atomicReferenceArray.length() - 1) & b;
            InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
            InternalEntry internalEntry2 = internalEntry;
            while (true) {
                if (internalEntry2 == null) {
                    break;
                }
                Object key = internalEntry2.getKey();
                if (internalEntry2.getHash() != b || key == null || !c2.c.i.equivalent(k2, key)) {
                    internalEntry2 = internalEntry2.getNext();
                } else {
                    Object value = internalEntry2.getValue();
                    if (value == null) {
                        if (internalEntry2.getValue() == null) {
                            c2.g++;
                            atomicReferenceArray.set(length, c2.i(internalEntry, internalEntry2));
                            c2.f--;
                        }
                    } else if (c2.c.j.valueStrength().b().equivalent(v, value)) {
                        c2.g++;
                        c2.l(internalEntry2, v2);
                        c2.unlock();
                        return true;
                    }
                }
            }
            return false;
        } finally {
            c2.unlock();
        }
    }

    public final /* synthetic */ void replaceAll(BiFunction biFunction) {
        ConcurrentMap.CC.$default$replaceAll(this, biFunction);
    }

    public int size() {
        Segment<K, V, E, S>[] segmentArr = this.g;
        long j2 = 0;
        for (Segment<K, V, E, S> segment : segmentArr) {
            j2 += (long) segment.f;
        }
        return y3.b(j2);
    }

    public Collection<V> values() {
        Collection<V> collection = this.l;
        if (collection != null) {
            return collection;
        }
        Values values = new Values();
        this.l = values;
        return values;
    }

    public Object writeReplace() {
        InternalEntryHelper<K, V, E, S> internalEntryHelper = this.j;
        Strength keyStrength = internalEntryHelper.keyStrength();
        Strength valueStrength = internalEntryHelper.valueStrength();
        Equivalence<Object> equivalence = this.i;
        internalEntryHelper.valueStrength().b();
        return new SerializationProxy(keyStrength, valueStrength, equivalence, this.h, this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005e, code lost:
        if ((r7.getValue() == null) != false) goto L_0x0060;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean remove(java.lang.Object r11, java.lang.Object r12) {
        /*
            r10 = this;
            r0 = 0
            if (r11 == 0) goto L_0x0080
            if (r12 != 0) goto L_0x0007
            goto L_0x0080
        L_0x0007:
            int r1 = r10.b(r11)
            com.google.common.collect.MapMakerInternalMap$Segment r2 = r10.c(r1)
            r2.lock()
            r2.j()     // Catch:{ all -> 0x007b }
            java.util.concurrent.atomic.AtomicReferenceArray<E> r3 = r2.i     // Catch:{ all -> 0x007b }
            int r4 = r3.length()     // Catch:{ all -> 0x007b }
            r5 = 1
            int r4 = r4 - r5
            r4 = r4 & r1
            java.lang.Object r6 = r3.get(r4)     // Catch:{ all -> 0x007b }
            com.google.common.collect.MapMakerInternalMap$InternalEntry r6 = (com.google.common.collect.MapMakerInternalMap.InternalEntry) r6     // Catch:{ all -> 0x007b }
            r7 = r6
        L_0x0025:
            if (r7 == 0) goto L_0x0077
            java.lang.Object r8 = r7.getKey()     // Catch:{ all -> 0x007b }
            int r9 = r7.getHash()     // Catch:{ all -> 0x007b }
            if (r9 != r1) goto L_0x0072
            if (r8 == 0) goto L_0x0072
            com.google.common.collect.MapMakerInternalMap<K, V, E, S> r9 = r2.c     // Catch:{ all -> 0x007b }
            com.google.common.base.Equivalence<java.lang.Object> r9 = r9.i     // Catch:{ all -> 0x007b }
            boolean r8 = r9.equivalent(r11, r8)     // Catch:{ all -> 0x007b }
            if (r8 == 0) goto L_0x0072
            java.lang.Object r11 = r7.getValue()     // Catch:{ all -> 0x007b }
            com.google.common.collect.MapMakerInternalMap<K, V, E, S> r1 = r2.c     // Catch:{ all -> 0x007b }
            com.google.common.collect.MapMakerInternalMap$InternalEntryHelper<K, V, E, S> r1 = r1.j     // Catch:{ all -> 0x007b }
            com.google.common.collect.MapMakerInternalMap$Strength r1 = r1.valueStrength()     // Catch:{ all -> 0x007b }
            com.google.common.base.Equivalence r1 = r1.b()     // Catch:{ all -> 0x007b }
            boolean r11 = r1.equivalent(r12, r11)     // Catch:{ all -> 0x007b }
            if (r11 == 0) goto L_0x0055
            r0 = 1
            goto L_0x0060
        L_0x0055:
            java.lang.Object r11 = r7.getValue()     // Catch:{ all -> 0x007b }
            if (r11 != 0) goto L_0x005d
            r11 = 1
            goto L_0x005e
        L_0x005d:
            r11 = 0
        L_0x005e:
            if (r11 == 0) goto L_0x0077
        L_0x0060:
            int r11 = r2.g     // Catch:{ all -> 0x007b }
            int r11 = r11 + r5
            r2.g = r11     // Catch:{ all -> 0x007b }
            com.google.common.collect.MapMakerInternalMap$InternalEntry r11 = r2.i(r6, r7)     // Catch:{ all -> 0x007b }
            int r12 = r2.f     // Catch:{ all -> 0x007b }
            int r12 = r12 - r5
            r3.set(r4, r11)     // Catch:{ all -> 0x007b }
            r2.f = r12     // Catch:{ all -> 0x007b }
            goto L_0x0077
        L_0x0072:
            com.google.common.collect.MapMakerInternalMap$InternalEntry r7 = r7.getNext()     // Catch:{ all -> 0x007b }
            goto L_0x0025
        L_0x0077:
            r2.unlock()
            return r0
        L_0x007b:
            r11 = move-exception
            r2.unlock()
            throw r11
        L_0x0080:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.MapMakerInternalMap.remove(java.lang.Object, java.lang.Object):boolean");
    }

    public V replace(K k2, V v) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(v);
        int b = b(k2);
        Segment c2 = c(b);
        c2.lock();
        try {
            c2.j();
            AtomicReferenceArray<E> atomicReferenceArray = c2.i;
            int length = (atomicReferenceArray.length() - 1) & b;
            InternalEntry internalEntry = (InternalEntry) atomicReferenceArray.get(length);
            InternalEntry internalEntry2 = internalEntry;
            while (true) {
                if (internalEntry2 == null) {
                    break;
                }
                Object key = internalEntry2.getKey();
                if (internalEntry2.getHash() != b || key == null || !c2.c.i.equivalent(k2, key)) {
                    internalEntry2 = internalEntry2.getNext();
                } else {
                    V value = internalEntry2.getValue();
                    if (value == null) {
                        if (internalEntry2.getValue() == null) {
                            c2.g++;
                            atomicReferenceArray.set(length, c2.i(internalEntry, internalEntry2));
                            c2.f--;
                        }
                    } else {
                        c2.g++;
                        c2.l(internalEntry2, v);
                        c2.unlock();
                        return value;
                    }
                }
            }
            return null;
        } finally {
            c2.unlock();
        }
    }
}
