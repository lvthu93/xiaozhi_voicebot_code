package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    public transient K[] c;
    public transient V[] f;
    public transient int g;
    public transient int h;
    public transient int[] i;
    public transient int[] j;
    public transient int[] k;
    public transient int[] l;
    public transient int m;
    public transient int n;
    public transient int[] o;
    public transient int[] p;
    public transient Set<K> q;
    public transient Set<V> r;
    public transient Set<Map.Entry<K, V>> s;
    public transient BiMap<V, K> t;

    public final class EntryForKey extends AbstractMapEntry<K, V> {
        public final K c;
        public int f;

        public EntryForKey(int i) {
            this.c = HashBiMap.this.c[i];
            this.f = i;
        }

        public final void a() {
            int i = this.f;
            K k = this.c;
            HashBiMap hashBiMap = HashBiMap.this;
            if (i == -1 || i > hashBiMap.g || !Objects.equal(hashBiMap.c[i], k)) {
                hashBiMap.getClass();
                this.f = hashBiMap.f(Hashing.c(k), k);
            }
        }

        public K getKey() {
            return this.c;
        }

        public V getValue() {
            a();
            int i = this.f;
            if (i == -1) {
                return null;
            }
            return HashBiMap.this.f[i];
        }

        public V setValue(V v) {
            a();
            int i = this.f;
            HashBiMap hashBiMap = HashBiMap.this;
            if (i == -1) {
                return hashBiMap.put(this.c, v);
            }
            V v2 = hashBiMap.f[i];
            if (Objects.equal(v2, v)) {
                return v;
            }
            hashBiMap.q(this.f, v, false);
            return v2;
        }
    }

    public static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
        public final HashBiMap<K, V> c;
        public final V f;
        public int g;

        public EntryForValue(HashBiMap<K, V> hashBiMap, int i) {
            this.c = hashBiMap;
            this.f = hashBiMap.f[i];
            this.g = i;
        }

        public final void a() {
            int i = this.g;
            V v = this.f;
            HashBiMap<K, V> hashBiMap = this.c;
            if (i == -1 || i > hashBiMap.g || !Objects.equal(v, hashBiMap.f[i])) {
                hashBiMap.getClass();
                this.g = hashBiMap.g(Hashing.c(v), v);
            }
        }

        public V getKey() {
            return this.f;
        }

        public K getValue() {
            a();
            int i = this.g;
            if (i == -1) {
                return null;
            }
            return this.c.c[i];
        }

        public K setValue(K k) {
            a();
            int i = this.g;
            HashBiMap<K, V> hashBiMap = this.c;
            if (i == -1) {
                return hashBiMap.l(this.f, k, false);
            }
            K k2 = hashBiMap.c[i];
            if (Objects.equal(k2, k)) {
                return k;
            }
            hashBiMap.p(this.g, k, false);
            return k2;
        }
    }

    public final class EntrySet extends View<K, V, Map.Entry<K, V>> {
        public EntrySet() {
            super(HashBiMap.this);
        }

        public final Object a(int i) {
            return new EntryForKey(i);
        }

        public boolean contains(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Object value = entry.getValue();
                HashBiMap hashBiMap = HashBiMap.this;
                hashBiMap.getClass();
                int f2 = hashBiMap.f(Hashing.c(key), key);
                if (f2 == -1 || !Objects.equal(value, hashBiMap.f[f2])) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int c = Hashing.c(key);
            HashBiMap hashBiMap = HashBiMap.this;
            int f2 = hashBiMap.f(c, key);
            if (f2 == -1 || !Objects.equal(value, hashBiMap.f[f2])) {
                return false;
            }
            hashBiMap.n(f2, c);
            return true;
        }
    }

    public static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        public final HashBiMap<K, V> c;
        public transient Set<Map.Entry<V, K>> f;

        public Inverse(HashBiMap<K, V> hashBiMap) {
            this.c = hashBiMap;
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.c.t = this;
        }

        public void clear() {
            this.c.clear();
        }

        public boolean containsKey(Object obj) {
            return this.c.containsValue(obj);
        }

        public boolean containsValue(Object obj) {
            return this.c.containsKey(obj);
        }

        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.f;
            if (set != null) {
                return set;
            }
            InverseEntrySet inverseEntrySet = new InverseEntrySet(this.c);
            this.f = inverseEntrySet;
            return inverseEntrySet;
        }

        public K forcePut(V v, K k) {
            return this.c.l(v, k, true);
        }

        public K get(Object obj) {
            HashBiMap<K, V> hashBiMap = this.c;
            hashBiMap.getClass();
            int g = hashBiMap.g(Hashing.c(obj), obj);
            if (g == -1) {
                return null;
            }
            return hashBiMap.c[g];
        }

        public BiMap<K, V> inverse() {
            return this.c;
        }

        public Set<V> keySet() {
            return this.c.values();
        }

        public K put(V v, K k) {
            return this.c.l(v, k, false);
        }

        public K remove(Object obj) {
            HashBiMap<K, V> hashBiMap = this.c;
            hashBiMap.getClass();
            int c2 = Hashing.c(obj);
            int g = hashBiMap.g(c2, obj);
            if (g == -1) {
                return null;
            }
            K k = hashBiMap.c[g];
            hashBiMap.o(g, c2);
            return k;
        }

        public int size() {
            return this.c.g;
        }

        public Set<K> values() {
            return this.c.keySet();
        }
    }

    public static class InverseEntrySet<K, V> extends View<K, V, Map.Entry<V, K>> {
        public InverseEntrySet(HashBiMap<K, V> hashBiMap) {
            super(hashBiMap);
        }

        public final Object a(int i) {
            return new EntryForValue(this.c, i);
        }

        public boolean contains(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                Object value = entry.getValue();
                HashBiMap<K, V> hashBiMap = this.c;
                hashBiMap.getClass();
                int g = hashBiMap.g(Hashing.c(key), key);
                if (g == -1 || !Objects.equal(hashBiMap.c[g], value)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int c = Hashing.c(key);
            HashBiMap<K, V> hashBiMap = this.c;
            int g = hashBiMap.g(c, key);
            if (g == -1 || !Objects.equal(hashBiMap.c[g], value)) {
                return false;
            }
            hashBiMap.o(g, c);
            return true;
        }
    }

    public final class KeySet extends View<K, V, K> {
        public KeySet() {
            super(HashBiMap.this);
        }

        public final K a(int i) {
            return HashBiMap.this.c[i];
        }

        public boolean contains(Object obj) {
            return HashBiMap.this.containsKey(obj);
        }

        public boolean remove(Object obj) {
            int c = Hashing.c(obj);
            HashBiMap hashBiMap = HashBiMap.this;
            int f2 = hashBiMap.f(c, obj);
            if (f2 == -1) {
                return false;
            }
            hashBiMap.n(f2, c);
            return true;
        }
    }

    public final class ValueSet extends View<K, V, V> {
        public ValueSet() {
            super(HashBiMap.this);
        }

        public final V a(int i) {
            return HashBiMap.this.f[i];
        }

        public boolean contains(Object obj) {
            return HashBiMap.this.containsValue(obj);
        }

        public boolean remove(Object obj) {
            int c = Hashing.c(obj);
            HashBiMap hashBiMap = HashBiMap.this;
            int g = hashBiMap.g(c, obj);
            if (g == -1) {
                return false;
            }
            hashBiMap.o(g, c);
            return true;
        }
    }

    public static abstract class View<K, V, T> extends AbstractSet<T> {
        public final HashBiMap<K, V> c;

        public View(HashBiMap<K, V> hashBiMap) {
            this.c = hashBiMap;
        }

        public abstract T a(int i);

        public void clear() {
            this.c.clear();
        }

        public Iterator<T> iterator() {
            return new Object() {
                public int c;
                public int f = -1;
                public int g;
                public int h;

                {
                    HashBiMap<K, V> hashBiMap = View.this.c;
                    this.c = hashBiMap.m;
                    this.g = hashBiMap.h;
                    this.h = hashBiMap.g;
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    if (View.this.c.h != this.g) {
                        throw new ConcurrentModificationException();
                    } else if (this.c == -2 || this.h <= 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                public T next() {
                    if (hasNext()) {
                        int i2 = this.c;
                        View view = View.this;
                        T a = view.a(i2);
                        int i3 = this.c;
                        this.f = i3;
                        this.c = view.c.p[i3];
                        this.h--;
                        return a;
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    boolean z;
                    View view = View.this;
                    if (view.c.h == this.g) {
                        if (this.f != -1) {
                            z = true;
                        } else {
                            z = false;
                        }
                        CollectPreconditions.e(z);
                        HashBiMap<K, V> hashBiMap = view.c;
                        int i2 = this.f;
                        hashBiMap.n(i2, Hashing.c(hashBiMap.c[i2]));
                        int i3 = this.c;
                        HashBiMap<K, V> hashBiMap2 = view.c;
                        if (i3 == hashBiMap2.g) {
                            this.c = this.f;
                        }
                        this.f = -1;
                        this.g = hashBiMap2.h;
                        return;
                    }
                    throw new ConcurrentModificationException();
                }
            };
        }

        public int size() {
            return this.c.g;
        }
    }

    public HashBiMap(int i2) {
        h(i2);
    }

    public static int[] b(int i2) {
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        h(16);
        Serialization.b(this, objectInputStream, readInt);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.e(this, objectOutputStream);
    }

    public final int a(int i2) {
        return i2 & (this.i.length - 1);
    }

    public final void c(int i2, int i3) {
        boolean z;
        if (i2 != -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        int a = a(i3);
        int[] iArr = this.i;
        int i4 = iArr[a];
        if (i4 == i2) {
            int[] iArr2 = this.k;
            iArr[a] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.k[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            int i7 = i6;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with key " + this.c[i2]);
            } else if (i4 == i2) {
                int[] iArr3 = this.k;
                iArr3[i7] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            } else {
                i5 = this.k[i4];
            }
        }
    }

    public void clear() {
        Arrays.fill(this.c, 0, this.g, (Object) null);
        Arrays.fill(this.f, 0, this.g, (Object) null);
        Arrays.fill(this.i, -1);
        Arrays.fill(this.j, -1);
        Arrays.fill(this.k, 0, this.g, -1);
        Arrays.fill(this.l, 0, this.g, -1);
        Arrays.fill(this.o, 0, this.g, -1);
        Arrays.fill(this.p, 0, this.g, -1);
        this.g = 0;
        this.m = -2;
        this.n = -2;
        this.h++;
    }

    public boolean containsKey(Object obj) {
        return f(Hashing.c(obj), obj) != -1;
    }

    public boolean containsValue(Object obj) {
        return g(Hashing.c(obj), obj) != -1;
    }

    public final void d(int i2, int i3) {
        boolean z;
        if (i2 != -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        int a = a(i3);
        int[] iArr = this.j;
        int i4 = iArr[a];
        if (i4 == i2) {
            int[] iArr2 = this.l;
            iArr[a] = iArr2[i2];
            iArr2[i2] = -1;
            return;
        }
        int i5 = this.l[i4];
        while (true) {
            int i6 = i4;
            i4 = i5;
            int i7 = i6;
            if (i4 == -1) {
                throw new AssertionError("Expected to find entry with value " + this.f[i2]);
            } else if (i4 == i2) {
                int[] iArr3 = this.l;
                iArr3[i7] = iArr3[i2];
                iArr3[i2] = -1;
                return;
            } else {
                i5 = this.l[i4];
            }
        }
    }

    public final void e(int i2) {
        int[] iArr = this.k;
        if (iArr.length < i2) {
            int a = ImmutableCollection.Builder.a(iArr.length, i2);
            this.c = Arrays.copyOf(this.c, a);
            this.f = Arrays.copyOf(this.f, a);
            int[] iArr2 = this.k;
            int length = iArr2.length;
            int[] copyOf = Arrays.copyOf(iArr2, a);
            Arrays.fill(copyOf, length, a, -1);
            this.k = copyOf;
            int[] iArr3 = this.l;
            int length2 = iArr3.length;
            int[] copyOf2 = Arrays.copyOf(iArr3, a);
            Arrays.fill(copyOf2, length2, a, -1);
            this.l = copyOf2;
            int[] iArr4 = this.o;
            int length3 = iArr4.length;
            int[] copyOf3 = Arrays.copyOf(iArr4, a);
            Arrays.fill(copyOf3, length3, a, -1);
            this.o = copyOf3;
            int[] iArr5 = this.p;
            int length4 = iArr5.length;
            int[] copyOf4 = Arrays.copyOf(iArr5, a);
            Arrays.fill(copyOf4, length4, a, -1);
            this.p = copyOf4;
        }
        if (this.i.length < i2) {
            int a2 = Hashing.a(i2, 1.0d);
            this.i = b(a2);
            this.j = b(a2);
            for (int i3 = 0; i3 < this.g; i3++) {
                int a3 = a(Hashing.c(this.c[i3]));
                int[] iArr6 = this.k;
                int[] iArr7 = this.i;
                iArr6[i3] = iArr7[a3];
                iArr7[a3] = i3;
                int a4 = a(Hashing.c(this.f[i3]));
                int[] iArr8 = this.l;
                int[] iArr9 = this.j;
                iArr8[i3] = iArr9[a4];
                iArr9[a4] = i3;
            }
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.s;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.s = entrySet;
        return entrySet;
    }

    public final int f(int i2, Object obj) {
        int[] iArr = this.i;
        int[] iArr2 = this.k;
        K[] kArr = this.c;
        for (int i3 = iArr[a(i2)]; i3 != -1; i3 = iArr2[i3]) {
            if (Objects.equal(kArr[i3], obj)) {
                return i3;
            }
        }
        return -1;
    }

    public V forcePut(K k2, V v) {
        return k(k2, v, true);
    }

    public final int g(int i2, Object obj) {
        int[] iArr = this.j;
        int[] iArr2 = this.l;
        V[] vArr = this.f;
        for (int i3 = iArr[a(i2)]; i3 != -1; i3 = iArr2[i3]) {
            if (Objects.equal(vArr[i3], obj)) {
                return i3;
            }
        }
        return -1;
    }

    public V get(Object obj) {
        int f2 = f(Hashing.c(obj), obj);
        if (f2 == -1) {
            return null;
        }
        return this.f[f2];
    }

    public final void h(int i2) {
        CollectPreconditions.b(i2, "expectedSize");
        int a = Hashing.a(i2, 1.0d);
        this.g = 0;
        this.c = new Object[i2];
        this.f = new Object[i2];
        this.i = b(a);
        this.j = b(a);
        this.k = b(i2);
        this.l = b(i2);
        this.m = -2;
        this.n = -2;
        this.o = b(i2);
        this.p = b(i2);
    }

    public final void i(int i2, int i3) {
        boolean z;
        if (i2 != -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        int a = a(i3);
        int[] iArr = this.k;
        int[] iArr2 = this.i;
        iArr[i2] = iArr2[a];
        iArr2[a] = i2;
    }

    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.t;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.t = inverse;
        return inverse;
    }

    public final void j(int i2, int i3) {
        boolean z;
        if (i2 != -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        int a = a(i3);
        int[] iArr = this.l;
        int[] iArr2 = this.j;
        iArr[i2] = iArr2[a];
        iArr2[a] = i2;
    }

    public final V k(K k2, V v, boolean z) {
        boolean z2;
        int c2 = Hashing.c(k2);
        int f2 = f(c2, k2);
        if (f2 != -1) {
            V v2 = this.f[f2];
            if (Objects.equal(v2, v)) {
                return v;
            }
            q(f2, v, z);
            return v2;
        }
        int c3 = Hashing.c(v);
        int g2 = g(c3, v);
        if (!z) {
            if (g2 == -1) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "Value already present: %s", (Object) v);
        } else if (g2 != -1) {
            o(g2, c3);
        }
        e(this.g + 1);
        K[] kArr = this.c;
        int i2 = this.g;
        kArr[i2] = k2;
        this.f[i2] = v;
        i(i2, c2);
        j(this.g, c3);
        r(this.n, this.g);
        r(this.g, -2);
        this.g++;
        this.h++;
        return null;
    }

    public Set<K> keySet() {
        Set<K> set = this.q;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.q = keySet;
        return keySet;
    }

    public final K l(V v, K k2, boolean z) {
        int i2;
        boolean z2;
        int c2 = Hashing.c(v);
        int g2 = g(c2, v);
        if (g2 != -1) {
            K k3 = this.c[g2];
            if (Objects.equal(k3, k2)) {
                return k2;
            }
            p(g2, k2, z);
            return k3;
        }
        int i3 = this.n;
        int c3 = Hashing.c(k2);
        int f2 = f(c3, k2);
        if (!z) {
            if (f2 == -1) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "Key already present: %s", (Object) k2);
        } else if (f2 != -1) {
            i3 = this.o[f2];
            n(f2, c3);
        }
        e(this.g + 1);
        K[] kArr = this.c;
        int i4 = this.g;
        kArr[i4] = k2;
        this.f[i4] = v;
        i(i4, c3);
        j(this.g, c2);
        if (i3 == -2) {
            i2 = this.m;
        } else {
            i2 = this.p[i3];
        }
        r(i3, this.g);
        r(this.g, i2);
        this.g++;
        this.h++;
        return null;
    }

    public final void m(int i2, int i3, int i4) {
        boolean z;
        if (i2 != -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        c(i2, i3);
        d(i2, i4);
        r(this.o[i2], this.p[i2]);
        int i5 = this.g - 1;
        if (i5 != i2) {
            int i6 = this.o[i5];
            int i7 = this.p[i5];
            r(i6, i2);
            r(i2, i7);
            K[] kArr = this.c;
            K k2 = kArr[i5];
            V[] vArr = this.f;
            V v = vArr[i5];
            kArr[i2] = k2;
            vArr[i2] = v;
            int a = a(Hashing.c(k2));
            int[] iArr = this.i;
            int i8 = iArr[a];
            if (i8 == i5) {
                iArr[a] = i2;
            } else {
                int i9 = this.k[i8];
                while (i9 != i5) {
                    i8 = i9;
                    i9 = this.k[i9];
                }
                this.k[i8] = i2;
            }
            int[] iArr2 = this.k;
            iArr2[i2] = iArr2[i5];
            iArr2[i5] = -1;
            int a2 = a(Hashing.c(v));
            int[] iArr3 = this.j;
            int i10 = iArr3[a2];
            if (i10 == i5) {
                iArr3[a2] = i2;
            } else {
                int i11 = this.l[i10];
                while (i11 != i5) {
                    i10 = i11;
                    i11 = this.l[i11];
                }
                this.l[i10] = i2;
            }
            int[] iArr4 = this.l;
            iArr4[i2] = iArr4[i5];
            iArr4[i5] = -1;
        }
        K[] kArr2 = this.c;
        int i12 = this.g;
        kArr2[i12 - 1] = null;
        this.f[i12 - 1] = null;
        this.g = i12 - 1;
        this.h++;
    }

    public final void n(int i2, int i3) {
        m(i2, i3, Hashing.c(this.f[i2]));
    }

    public final void o(int i2, int i3) {
        m(i2, Hashing.c(this.c[i2]), i3);
    }

    public final void p(int i2, K k2, boolean z) {
        boolean z2;
        int i3;
        if (i2 != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        int c2 = Hashing.c(k2);
        int f2 = f(c2, k2);
        int i4 = this.n;
        if (f2 == -1) {
            i3 = -2;
        } else if (z) {
            i4 = this.o[f2];
            i3 = this.p[f2];
            n(f2, c2);
            if (i2 == this.g) {
                i2 = f2;
            }
        } else {
            throw new IllegalArgumentException("Key already present in map: " + k2);
        }
        if (i4 == i2) {
            i4 = this.o[i2];
        } else if (i4 == this.g) {
            i4 = f2;
        }
        if (i3 == i2) {
            f2 = this.p[i2];
        } else if (i3 != this.g) {
            f2 = i3;
        }
        r(this.o[i2], this.p[i2]);
        c(i2, Hashing.c(this.c[i2]));
        this.c[i2] = k2;
        i(i2, Hashing.c(k2));
        r(i4, i2);
        r(i2, f2);
    }

    public V put(K k2, V v) {
        return k(k2, v, false);
    }

    public final void q(int i2, V v, boolean z) {
        boolean z2;
        if (i2 != -1) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        int c2 = Hashing.c(v);
        int g2 = g(c2, v);
        if (g2 != -1) {
            if (z) {
                o(g2, c2);
                if (i2 == this.g) {
                    i2 = g2;
                }
            } else {
                throw new IllegalArgumentException("Value already present in map: " + v);
            }
        }
        d(i2, Hashing.c(this.f[i2]));
        this.f[i2] = v;
        j(i2, c2);
    }

    public final void r(int i2, int i3) {
        if (i2 == -2) {
            this.m = i3;
        } else {
            this.p[i2] = i3;
        }
        if (i3 == -2) {
            this.n = i2;
        } else {
            this.o[i3] = i2;
        }
    }

    public V remove(Object obj) {
        int c2 = Hashing.c(obj);
        int f2 = f(c2, obj);
        if (f2 == -1) {
            return null;
        }
        V v = this.f[f2];
        n(f2, c2);
        return v;
    }

    public int size() {
        return this.g;
    }

    public static <K, V> HashBiMap<K, V> create(int i2) {
        return new HashBiMap<>(i2);
    }

    public Set<V> values() {
        Set<V> set = this.r;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.r = valueSet;
        return valueSet;
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }
}
