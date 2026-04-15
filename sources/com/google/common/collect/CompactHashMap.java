package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static final /* synthetic */ int p = 0;
    public transient int[] c;
    public transient long[] f;
    public transient Object[] g;
    public transient Object[] h;
    public transient float i;
    public transient int j;
    public transient int k;
    public transient int l;
    public transient Set<K> m;
    public transient Set<Map.Entry<K, V>> n;
    public transient Collection<V> o;

    public class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        public EntrySetView() {
        }

        public void clear() {
            CompactHashMap.this.clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            int i = CompactHashMap.p;
            CompactHashMap compactHashMap = CompactHashMap.this;
            int f = compactHashMap.f(key);
            if (f == -1 || !Objects.equal(compactHashMap.h[f], entry.getValue())) {
                return false;
            }
            return true;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.getClass();
            return new CompactHashMap<Object, Object>.Itr<Map.Entry<Object, Object>>() {
                public final Object a(int i2) {
                    return new MapEntry(i2);
                }
            };
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            int i = CompactHashMap.p;
            CompactHashMap compactHashMap = CompactHashMap.this;
            int f = compactHashMap.f(key);
            if (f == -1 || !Objects.equal(compactHashMap.h[f], entry.getValue())) {
                return false;
            }
            CompactHashMap.a(compactHashMap, f);
            return true;
        }

        public int size() {
            return CompactHashMap.this.l;
        }
    }

    public abstract class Itr<T> implements Iterator<T>, j$.util.Iterator {
        public int c;
        public int f;
        public int g = -1;

        public Itr() {
            this.c = CompactHashMap.this.j;
            this.f = CompactHashMap.this.d();
        }

        public abstract T a(int i);

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.f >= 0;
        }

        public T next() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            if (compactHashMap.j != this.c) {
                throw new ConcurrentModificationException();
            } else if (hasNext()) {
                int i = this.f;
                this.g = i;
                T a = a(i);
                this.f = compactHashMap.e(this.f);
                return a;
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            boolean z;
            CompactHashMap compactHashMap = CompactHashMap.this;
            if (compactHashMap.j == this.c) {
                if (this.g >= 0) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                this.c++;
                CompactHashMap.a(compactHashMap, this.g);
                this.f = compactHashMap.c(this.f, this.g);
                this.g = -1;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    public class KeySetView extends AbstractSet<K> {
        public KeySetView() {
        }

        public void clear() {
            CompactHashMap.this.clear();
        }

        public boolean contains(Object obj) {
            return CompactHashMap.this.containsKey(obj);
        }

        public java.util.Iterator<K> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.getClass();
            return new CompactHashMap<Object, Object>.Itr<Object>() {
                public final Object a(int i2) {
                    return CompactHashMap.this.g[i2];
                }
            };
        }

        public boolean remove(Object obj) {
            int i = CompactHashMap.p;
            CompactHashMap compactHashMap = CompactHashMap.this;
            int f = compactHashMap.f(obj);
            if (f == -1) {
                return false;
            }
            CompactHashMap.a(compactHashMap, f);
            return true;
        }

        public int size() {
            return CompactHashMap.this.l;
        }
    }

    public final class MapEntry extends AbstractMapEntry<K, V> {
        public final K c;
        public int f;

        public MapEntry(int i) {
            this.c = CompactHashMap.this.g[i];
            this.f = i;
        }

        public final void a() {
            int i = this.f;
            K k = this.c;
            CompactHashMap compactHashMap = CompactHashMap.this;
            if (i == -1 || i >= compactHashMap.size() || !Objects.equal(k, compactHashMap.g[this.f])) {
                int i2 = CompactHashMap.p;
                this.f = compactHashMap.f(k);
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
            return CompactHashMap.this.h[i];
        }

        public V setValue(V v) {
            a();
            int i = this.f;
            CompactHashMap compactHashMap = CompactHashMap.this;
            if (i == -1) {
                compactHashMap.put(this.c, v);
                return null;
            }
            V[] vArr = compactHashMap.h;
            V v2 = vArr[i];
            vArr[i] = v;
            return v2;
        }
    }

    public class ValuesView extends AbstractCollection<V> {
        public ValuesView() {
        }

        public void clear() {
            CompactHashMap.this.clear();
        }

        public java.util.Iterator<V> iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.getClass();
            return new CompactHashMap<Object, Object>.Itr<Object>() {
                public final Object a(int i2) {
                    return CompactHashMap.this.h[i2];
                }
            };
        }

        public int size() {
            return CompactHashMap.this.l;
        }
    }

    public CompactHashMap() {
        g(3);
    }

    public static void a(CompactHashMap compactHashMap, int i2) {
        compactHashMap.j((int) (compactHashMap.f[i2] >>> 32), compactHashMap.g[i2]);
    }

    public static <K, V> CompactHashMap<K, V> create() {
        return new CompactHashMap<>();
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int i2) {
        return new CompactHashMap<>(i2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        g(3);
        int readInt = objectInputStream.readInt();
        while (true) {
            readInt--;
            if (readInt >= 0) {
                put(objectInputStream.readObject(), objectInputStream.readObject());
            } else {
                return;
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.l);
        for (int i2 = 0; i2 < this.l; i2++) {
            objectOutputStream.writeObject(this.g[i2]);
            objectOutputStream.writeObject(this.h[i2]);
        }
    }

    public void b(int i2) {
    }

    public int c(int i2, int i3) {
        return i2 - 1;
    }

    public void clear() {
        this.j++;
        Arrays.fill(this.g, 0, this.l, (Object) null);
        Arrays.fill(this.h, 0, this.l, (Object) null);
        Arrays.fill(this.c, -1);
        Arrays.fill(this.f, -1);
        this.l = 0;
    }

    public boolean containsKey(Object obj) {
        return f(obj) != -1;
    }

    public boolean containsValue(Object obj) {
        for (int i2 = 0; i2 < this.l; i2++) {
            if (Objects.equal(obj, this.h[i2])) {
                return true;
            }
        }
        return false;
    }

    public int d() {
        return isEmpty() ? -1 : 0;
    }

    public int e(int i2) {
        int i3 = i2 + 1;
        if (i3 < this.l) {
            return i3;
        }
        return -1;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.n;
        if (set != null) {
            return set;
        }
        EntrySetView entrySetView = new EntrySetView();
        this.n = entrySetView;
        return entrySetView;
    }

    public final int f(Object obj) {
        int c2 = Hashing.c(obj);
        int[] iArr = this.c;
        int i2 = iArr[(iArr.length - 1) & c2];
        while (i2 != -1) {
            long j2 = this.f[i2];
            if (((int) (j2 >>> 32)) == c2 && Objects.equal(obj, this.g[i2])) {
                return i2;
            }
            i2 = (int) j2;
        }
        return -1;
    }

    public void g(int i2) {
        boolean z;
        if (i2 >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Initial capacity must be non-negative");
        Preconditions.checkArgument(true, "Illegal load factor");
        int a = Hashing.a(i2, (double) 1.0f);
        int[] iArr = new int[a];
        Arrays.fill(iArr, -1);
        this.c = iArr;
        this.i = 1.0f;
        this.g = new Object[i2];
        this.h = new Object[i2];
        long[] jArr = new long[i2];
        Arrays.fill(jArr, -1);
        this.f = jArr;
        this.k = Math.max(1, (int) (((float) a) * 1.0f));
    }

    public V get(Object obj) {
        int f2 = f(obj);
        b(f2);
        if (f2 == -1) {
            return null;
        }
        return this.h[f2];
    }

    public void h(int i2, K k2, V v, int i3) {
        this.f[i2] = (((long) i3) << 32) | 4294967295L;
        this.g[i2] = k2;
        this.h[i2] = v;
    }

    public void i(int i2) {
        int size = size() - 1;
        if (i2 < size) {
            Object[] objArr = this.g;
            objArr[i2] = objArr[size];
            Object[] objArr2 = this.h;
            objArr2[i2] = objArr2[size];
            objArr[size] = null;
            objArr2[size] = null;
            long[] jArr = this.f;
            long j2 = jArr[size];
            jArr[i2] = j2;
            jArr[size] = -1;
            int[] iArr = this.c;
            int length = ((int) (j2 >>> 32)) & (iArr.length - 1);
            int i3 = iArr[length];
            if (i3 == size) {
                iArr[length] = i2;
                return;
            }
            while (true) {
                long[] jArr2 = this.f;
                long j3 = jArr2[i3];
                int i4 = (int) j3;
                if (i4 == size) {
                    jArr2[i3] = (j3 & -4294967296L) | (4294967295L & ((long) i2));
                    return;
                }
                i3 = i4;
            }
        } else {
            this.g[i2] = null;
            this.h[i2] = null;
            this.f[i2] = -1;
        }
    }

    public boolean isEmpty() {
        return this.l == 0;
    }

    public final Object j(int i2, Object obj) {
        int[] iArr = this.c;
        int length = (iArr.length - 1) & i2;
        int i3 = iArr[length];
        if (i3 == -1) {
            return null;
        }
        int i4 = -1;
        while (true) {
            if (((int) (this.f[i3] >>> 32)) != i2 || !Objects.equal(obj, this.g[i3])) {
                int i5 = (int) this.f[i3];
                if (i5 == -1) {
                    return null;
                }
                int i6 = i5;
                i4 = i3;
                i3 = i6;
            } else {
                Object obj2 = this.h[i3];
                if (i4 == -1) {
                    this.c[length] = (int) this.f[i3];
                } else {
                    long[] jArr = this.f;
                    jArr[i4] = (jArr[i4] & -4294967296L) | (4294967295L & ((long) ((int) jArr[i3])));
                }
                i(i3);
                this.l--;
                this.j++;
                return obj2;
            }
        }
    }

    public void k(int i2) {
        this.g = Arrays.copyOf(this.g, i2);
        this.h = Arrays.copyOf(this.h, i2);
        long[] jArr = this.f;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i2);
        if (i2 > length) {
            Arrays.fill(copyOf, length, i2, -1);
        }
        this.f = copyOf;
    }

    public Set<K> keySet() {
        Set<K> set = this.m;
        if (set != null) {
            return set;
        }
        KeySetView keySetView = new KeySetView();
        this.m = keySetView;
        return keySetView;
    }

    public final void l(int i2) {
        if (this.c.length >= 1073741824) {
            this.k = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            return;
        }
        int i3 = ((int) (((float) i2) * this.i)) + 1;
        int[] iArr = new int[i2];
        Arrays.fill(iArr, -1);
        long[] jArr = this.f;
        int i4 = i2 - 1;
        for (int i5 = 0; i5 < this.l; i5++) {
            int i6 = (int) (jArr[i5] >>> 32);
            int i7 = i6 & i4;
            int i8 = iArr[i7];
            iArr[i7] = i5;
            jArr[i5] = (((long) i6) << 32) | (((long) i8) & 4294967295L);
        }
        this.k = i3;
        this.c = iArr;
    }

    public V put(K k2, V v) {
        long[] jArr = this.f;
        Object[] objArr = this.g;
        V[] vArr = this.h;
        int c2 = Hashing.c(k2);
        int[] iArr = this.c;
        int length = (iArr.length - 1) & c2;
        int i2 = this.l;
        int i3 = iArr[length];
        if (i3 == -1) {
            iArr[length] = i2;
        } else {
            while (true) {
                long j2 = jArr[i3];
                if (((int) (j2 >>> 32)) != c2 || !Objects.equal(k2, objArr[i3])) {
                    int i4 = (int) j2;
                    if (i4 == -1) {
                        jArr[i3] = (-4294967296L & j2) | (4294967295L & ((long) i2));
                        break;
                    }
                    i3 = i4;
                } else {
                    V v2 = vArr[i3];
                    vArr[i3] = v;
                    b(i3);
                    return v2;
                }
            }
        }
        int i5 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        if (i2 != Integer.MAX_VALUE) {
            int i6 = i2 + 1;
            int length2 = this.f.length;
            if (i6 > length2) {
                int max = Math.max(1, length2 >>> 1) + length2;
                if (max >= 0) {
                    i5 = max;
                }
                if (i5 != length2) {
                    k(i5);
                }
            }
            h(i2, k2, v, c2);
            this.l = i6;
            if (i2 >= this.k) {
                l(this.c.length * 2);
            }
            this.j++;
            return null;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public V remove(Object obj) {
        return j(Hashing.c(obj), obj);
    }

    public int size() {
        return this.l;
    }

    public void trimToSize() {
        int i2 = this.l;
        if (i2 < this.f.length) {
            k(i2);
        }
        int max = Math.max(1, Integer.highestOneBit((int) (((float) i2) / this.i)));
        if (max < 1073741824 && ((double) i2) / ((double) max) > ((double) this.i)) {
            max <<= 1;
        }
        if (max < this.c.length) {
            l(max);
        }
    }

    public Collection<V> values() {
        Collection<V> collection = this.o;
        if (collection != null) {
            return collection;
        }
        ValuesView valuesView = new ValuesView();
        this.o = valuesView;
        return valuesView;
    }

    public CompactHashMap(int i2) {
        g(i2);
    }
}
