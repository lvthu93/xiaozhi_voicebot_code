package com.google.common.collect;

import com.google.common.collect.Maps;
import j$.util.Iterator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public abstract class ForwardingNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V> {

    public class StandardDescendingMap extends Maps.DescendingMap<K, V> {
        public StandardDescendingMap() {
        }

        public final Iterator<Map.Entry<K, V>> c() {
            return new Object() {
                public Map.Entry<K, V> c = null;
                public Map.Entry<K, V> f;

                {
                    this.f = ForwardingNavigableMap.this.lastEntry();
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return this.f != null;
                }

                public void remove() {
                    boolean z;
                    if (this.c != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    CollectPreconditions.e(z);
                    ForwardingNavigableMap.this.remove(this.c.getKey());
                    this.c = null;
                }

                public Map.Entry<K, V> next() {
                    StandardDescendingMap standardDescendingMap = StandardDescendingMap.this;
                    if (hasNext()) {
                        try {
                            Map.Entry<K, V> entry = this.f;
                            this.c = entry;
                            this.f = ForwardingNavigableMap.this.lowerEntry(entry.getKey());
                            return entry;
                        } catch (Throwable th) {
                            this.c = this.f;
                            this.f = ForwardingNavigableMap.this.lowerEntry(this.f.getKey());
                            throw th;
                        }
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }

        public final NavigableMap<K, V> f() {
            return ForwardingNavigableMap.this;
        }
    }

    public class StandardNavigableKeySet extends Maps.NavigableKeySet<K, V> {
        public StandardNavigableKeySet(ForwardingNavigableMap forwardingNavigableMap) {
            super(forwardingNavigableMap);
        }
    }

    public Map.Entry<K, V> ceilingEntry(K k) {
        return delegate().ceilingEntry(k);
    }

    public K ceilingKey(K k) {
        return delegate().ceilingKey(k);
    }

    public NavigableSet<K> descendingKeySet() {
        return delegate().descendingKeySet();
    }

    public NavigableMap<K, V> descendingMap() {
        return delegate().descendingMap();
    }

    /* renamed from: f */
    public abstract NavigableMap<K, V> delegate();

    public Map.Entry<K, V> firstEntry() {
        return delegate().firstEntry();
    }

    public Map.Entry<K, V> floorEntry(K k) {
        return delegate().floorEntry(k);
    }

    public K floorKey(K k) {
        return delegate().floorKey(k);
    }

    public NavigableMap<K, V> headMap(K k, boolean z) {
        return delegate().headMap(k, z);
    }

    public Map.Entry<K, V> higherEntry(K k) {
        return delegate().higherEntry(k);
    }

    public K higherKey(K k) {
        return delegate().higherKey(k);
    }

    public Map.Entry<K, V> lastEntry() {
        return delegate().lastEntry();
    }

    public Map.Entry<K, V> lowerEntry(K k) {
        return delegate().lowerEntry(k);
    }

    public K lowerKey(K k) {
        return delegate().lowerKey(k);
    }

    public NavigableSet<K> navigableKeySet() {
        return delegate().navigableKeySet();
    }

    public Map.Entry<K, V> pollFirstEntry() {
        return delegate().pollFirstEntry();
    }

    public Map.Entry<K, V> pollLastEntry() {
        return delegate().pollLastEntry();
    }

    public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
        return delegate().subMap(k, z, k2, z2);
    }

    public NavigableMap<K, V> tailMap(K k, boolean z) {
        return delegate().tailMap(k, z);
    }
}
