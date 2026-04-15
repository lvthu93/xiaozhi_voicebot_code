package com.google.protobuf;

import j$.util.Iterator;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;

public class af<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    public static final /* synthetic */ int k = 0;
    public Object[] c;
    public int f;
    public Map<K, V> g = Collections.emptyMap();
    public boolean h;
    public volatile af<K, V>.defpackage.c i;
    public Map<K, V> j = Collections.emptyMap();

    public class a implements Map.Entry<K, V>, Comparable<af<K, V>.defpackage.a> {
        public final K c;
        public V f;

        public a() {
            throw null;
        }

        public a(K k, V v) {
            this.c = k;
            this.f = v;
        }

        public final int compareTo(Object obj) {
            return this.c.compareTo(((a) obj).c);
        }

        public final boolean equals(Object obj) {
            boolean z;
            boolean z2;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            K k = this.c;
            if (k != null) {
                z = k.equals(key);
            } else if (key == null) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                V v = this.f;
                Object value = entry.getValue();
                if (v != null) {
                    z2 = v.equals(value);
                } else if (value == null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return true;
                }
            }
            return false;
        }

        public final Object getKey() {
            return this.c;
        }

        public final V getValue() {
            return this.f;
        }

        public final int hashCode() {
            int i = 0;
            K k = this.c;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.f;
            if (v != null) {
                i = v.hashCode();
            }
            return i ^ hashCode;
        }

        public final V setValue(V v) {
            int i = af.k;
            af.this.b();
            V v2 = this.f;
            this.f = v;
            return v2;
        }

        public final String toString() {
            return this.c + "=" + this.f;
        }
    }

    public class b implements Iterator<Map.Entry<K, V>>, j$.util.Iterator {
        public int c = -1;
        public boolean f;
        public Iterator<Map.Entry<K, V>> g;

        public b() {
        }

        public final Iterator<Map.Entry<K, V>> a() {
            if (this.g == null) {
                this.g = af.this.g.entrySet().iterator();
            }
            return this.g;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public final boolean hasNext() {
            int i = this.c + 1;
            af afVar = af.this;
            if (i < afVar.f) {
                return true;
            }
            if (afVar.g.isEmpty() || !a().hasNext()) {
                return false;
            }
            return true;
        }

        public final Object next() {
            this.f = true;
            int i = this.c + 1;
            this.c = i;
            af afVar = af.this;
            if (i < afVar.f) {
                return (a) afVar.c[i];
            }
            return (Map.Entry) a().next();
        }

        public final void remove() {
            if (this.f) {
                this.f = false;
                int i = af.k;
                af afVar = af.this;
                afVar.b();
                int i2 = this.c;
                if (i2 < afVar.f) {
                    this.c = i2 - 1;
                    afVar.h(i2);
                    return;
                }
                a().remove();
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }
    }

    public class c extends AbstractSet<Map.Entry<K, V>> {
        public c() {
        }

        public final boolean add(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            if (contains(entry)) {
                return false;
            }
            Object value = entry.getValue();
            af.this.put((Comparable) entry.getKey(), value);
            return true;
        }

        public final void clear() {
            af.this.clear();
        }

        public final boolean contains(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = af.this.get(entry.getKey());
            Object value = entry.getValue();
            if (obj2 == value || (obj2 != null && obj2.equals(value))) {
                return true;
            }
            return false;
        }

        public java.util.Iterator<Map.Entry<K, V>> iterator() {
            return new b();
        }

        public final boolean remove(Object obj) {
            Map.Entry entry = (Map.Entry) obj;
            if (!contains(entry)) {
                return false;
            }
            af.this.remove(entry.getKey());
            return true;
        }

        public final int size() {
            return af.this.size();
        }
    }

    public final int a(K k2) {
        int i2 = this.f - 1;
        if (i2 >= 0) {
            int compareTo = k2.compareTo(((a) this.c[i2]).c);
            if (compareTo > 0) {
                return -(i2 + 2);
            }
            if (compareTo == 0) {
                return i2;
            }
        }
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) / 2;
            int compareTo2 = k2.compareTo(((a) this.c[i4]).c);
            if (compareTo2 < 0) {
                i2 = i4 - 1;
            } else if (compareTo2 <= 0) {
                return i4;
            } else {
                i3 = i4 + 1;
            }
        }
        return -(i3 + 1);
    }

    public final void b() {
        if (this.h) {
            throw new UnsupportedOperationException();
        }
    }

    public final a c(int i2) {
        if (i2 < this.f) {
            return (a) this.c[i2];
        }
        throw new ArrayIndexOutOfBoundsException(i2);
    }

    public final void clear() {
        b();
        if (this.f != 0) {
            this.c = null;
            this.f = 0;
        }
        if (!this.g.isEmpty()) {
            this.g.clear();
        }
    }

    public final boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        if (a(comparable) >= 0 || this.g.containsKey(comparable)) {
            return true;
        }
        return false;
    }

    public final Set d() {
        if (this.g.isEmpty()) {
            return Collections.emptySet();
        }
        return this.g.entrySet();
    }

    public final SortedMap<K, V> e() {
        b();
        if (this.g.isEmpty() && !(this.g instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.g = treeMap;
            this.j = treeMap.descendingMap();
        }
        return (SortedMap) this.g;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        if (this.i == null) {
            this.i = new c();
        }
        return this.i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof af)) {
            return super.equals(obj);
        }
        af afVar = (af) obj;
        int size = size();
        if (size != afVar.size()) {
            return false;
        }
        int i2 = this.f;
        if (i2 != afVar.f) {
            return ((AbstractSet) entrySet()).equals(afVar.entrySet());
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (!c(i3).equals(afVar.c(i3))) {
                return false;
            }
        }
        if (i2 != size) {
            return this.g.equals(afVar.g);
        }
        return true;
    }

    public void f() {
        Map<K, V> map;
        Map<K, V> map2;
        if (!this.h) {
            if (this.g.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = Collections.unmodifiableMap(this.g);
            }
            this.g = map;
            if (this.j.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(this.j);
            }
            this.j = map2;
            this.h = true;
        }
    }

    /* renamed from: g */
    public final V put(K k2, V v) {
        b();
        int a2 = a(k2);
        if (a2 >= 0) {
            return ((a) this.c[a2]).setValue(v);
        }
        b();
        if (this.c == null) {
            this.c = new Object[16];
        }
        int i2 = -(a2 + 1);
        if (i2 >= 16) {
            return e().put(k2, v);
        }
        int i3 = this.f;
        if (i3 == 16) {
            a aVar = (a) this.c[15];
            this.f = i3 - 1;
            e().put(aVar.c, aVar.f);
        }
        Object[] objArr = this.c;
        System.arraycopy(objArr, i2, objArr, i2 + 1, (objArr.length - i2) - 1);
        this.c[i2] = new a(k2, v);
        this.f++;
        return null;
    }

    public final V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int a2 = a(comparable);
        if (a2 >= 0) {
            return ((a) this.c[a2]).f;
        }
        return this.g.get(comparable);
    }

    public final V h(int i2) {
        b();
        Object[] objArr = this.c;
        V v = ((a) objArr[i2]).f;
        System.arraycopy(objArr, i2 + 1, objArr, i2, (this.f - i2) - 1);
        this.f--;
        if (!this.g.isEmpty()) {
            java.util.Iterator it = e().entrySet().iterator();
            Object[] objArr2 = this.c;
            int i3 = this.f;
            Map.Entry entry = (Map.Entry) it.next();
            objArr2[i3] = new a((Comparable) entry.getKey(), entry.getValue());
            this.f++;
            it.remove();
        }
        return v;
    }

    public final int hashCode() {
        int i2 = this.f;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += this.c[i4].hashCode();
        }
        if (this.g.size() > 0) {
            return i3 + this.g.hashCode();
        }
        return i3;
    }

    public final V remove(Object obj) {
        b();
        Comparable comparable = (Comparable) obj;
        int a2 = a(comparable);
        if (a2 >= 0) {
            return h(a2);
        }
        if (this.g.isEmpty()) {
            return null;
        }
        return this.g.remove(comparable);
    }

    public final int size() {
        return this.g.size() + this.f;
    }
}
