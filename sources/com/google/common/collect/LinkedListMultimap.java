package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public class LinkedListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    public transient Node<K, V> j;
    public transient Node<K, V> k;
    public transient Map<K, KeyList<K, V>> l;
    public transient int m;
    public transient int n;

    public class DistinctKeyIterator implements Iterator<K>, j$.util.Iterator {
        public final HashSet c;
        public Node<K, V> f;
        public Node<K, V> g;
        public int h;

        public DistinctKeyIterator() {
            this.c = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.f = LinkedListMultimap.this.j;
            this.h = LinkedListMultimap.this.n;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            if (LinkedListMultimap.this.n != this.h) {
                throw new ConcurrentModificationException();
            } else if (this.f != null) {
                return true;
            } else {
                return false;
            }
        }

        public K next() {
            Node<K, V> node;
            if (LinkedListMultimap.this.n == this.h) {
                Node<K, V> node2 = this.f;
                if (node2 != null) {
                    this.g = node2;
                    K k = node2.c;
                    HashSet hashSet = this.c;
                    hashSet.add(k);
                    do {
                        node = this.f.g;
                        this.f = node;
                        if (node == null || hashSet.add(node.c)) {
                        }
                        node = this.f.g;
                        this.f = node;
                        break;
                    } while (hashSet.add(node.c));
                    return this.g.c;
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        public void remove() {
            boolean z;
            LinkedListMultimap linkedListMultimap = LinkedListMultimap.this;
            if (linkedListMultimap.n == this.h) {
                if (this.g != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                K k = this.g.c;
                linkedListMultimap.getClass();
                Iterators.b(new ValueForKeyIterator(k));
                this.g = null;
                this.h = linkedListMultimap.n;
                return;
            }
            throw new ConcurrentModificationException();
        }
    }

    public static class KeyList<K, V> {
        public Node<K, V> a;
        public Node<K, V> b;
        public int c = 1;

        public KeyList(Node<K, V> node) {
            this.a = node;
            this.b = node;
            node.j = null;
            node.i = null;
        }
    }

    public static final class Node<K, V> extends AbstractMapEntry<K, V> {
        public final K c;
        public V f;
        public Node<K, V> g;
        public Node<K, V> h;
        public Node<K, V> i;
        public Node<K, V> j;

        public Node(K k, V v) {
            this.c = k;
            this.f = v;
        }

        public K getKey() {
            return this.c;
        }

        public V getValue() {
            return this.f;
        }

        public V setValue(V v) {
            V v2 = this.f;
            this.f = v;
            return v2;
        }
    }

    public class NodeIterator implements ListIterator<Map.Entry<K, V>>, j$.util.Iterator {
        public int c;
        public Node<K, V> f;
        public Node<K, V> g;
        public Node<K, V> h;
        public int i;

        public NodeIterator(int i2) {
            this.i = LinkedListMultimap.this.n;
            int size = LinkedListMultimap.this.size();
            Preconditions.checkPositionIndex(i2, size);
            if (i2 < size / 2) {
                this.f = LinkedListMultimap.this.j;
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    next();
                    i2 = i3;
                }
            } else {
                this.h = LinkedListMultimap.this.k;
                this.c = size;
                while (true) {
                    int i4 = i2 + 1;
                    if (i2 >= size) {
                        break;
                    }
                    previous();
                    i2 = i4;
                }
            }
            this.g = null;
        }

        public final void a() {
            if (LinkedListMultimap.this.n != this.i) {
                throw new ConcurrentModificationException();
            }
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            a();
            if (this.f != null) {
                return true;
            }
            return false;
        }

        public boolean hasPrevious() {
            a();
            if (this.h != null) {
                return true;
            }
            return false;
        }

        public int nextIndex() {
            return this.c;
        }

        public int previousIndex() {
            return this.c - 1;
        }

        public void remove() {
            boolean z;
            a();
            if (this.g != null) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.e(z);
            Node<K, V> node = this.g;
            if (node != this.f) {
                this.h = node.h;
                this.c--;
            } else {
                this.f = node.g;
            }
            LinkedListMultimap linkedListMultimap = LinkedListMultimap.this;
            LinkedListMultimap.h(linkedListMultimap, node);
            this.g = null;
            this.i = linkedListMultimap.n;
        }

        public void add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        public Node<K, V> next() {
            a();
            Node<K, V> node = this.f;
            if (node != null) {
                this.g = node;
                this.h = node;
                this.f = node.g;
                this.c++;
                return node;
            }
            throw new NoSuchElementException();
        }

        public Node<K, V> previous() {
            a();
            Node<K, V> node = this.h;
            if (node != null) {
                this.g = node;
                this.f = node;
                this.h = node.h;
                this.c--;
                return node;
            }
            throw new NoSuchElementException();
        }

        public void set(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }
    }

    public LinkedListMultimap() {
        this(12);
    }

    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<>();
    }

    public static void h(LinkedListMultimap linkedListMultimap, Node node) {
        linkedListMultimap.getClass();
        Node<K, V> node2 = node.h;
        if (node2 != null) {
            node2.g = node.g;
        } else {
            linkedListMultimap.j = node.g;
        }
        Node<K, V> node3 = node.g;
        if (node3 != null) {
            node3.h = node2;
        } else {
            linkedListMultimap.k = node2;
        }
        Node<K, V> node4 = node.j;
        K k2 = node.c;
        if (node4 == null && node.i == null) {
            linkedListMultimap.l.remove(k2).c = 0;
            linkedListMultimap.n++;
        } else {
            KeyList keyList = linkedListMultimap.l.get(k2);
            keyList.c--;
            Node<K, V> node5 = node.j;
            if (node5 == null) {
                keyList.a = node.i;
            } else {
                node5.i = node.i;
            }
            Node<K, V> node6 = node.i;
            if (node6 == null) {
                keyList.b = node5;
            } else {
                node6.j = node5;
            }
        }
        linkedListMultimap.m--;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.l = CompactLinkedHashMap.create();
        int readInt = objectInputStream.readInt();
        for (int i = 0; i < readInt; i++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        for (Map.Entry entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    public final Map<K, Collection<V>> a() {
        return new Multimaps.AsMap(this);
    }

    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    public final Collection b() {
        return new AbstractSequentialList<Map.Entry<Object, Object>>() {
            public ListIterator<Map.Entry<Object, Object>> listIterator(int i) {
                return new NodeIterator(i);
            }

            public int size() {
                return LinkedListMultimap.this.m;
            }
        };
    }

    public final Set<K> c() {
        return new Sets.ImprovedAbstractSet<K>() {
            public boolean contains(Object obj) {
                return LinkedListMultimap.this.containsKey(obj);
            }

            public java.util.Iterator<K> iterator() {
                return new DistinctKeyIterator();
            }

            public boolean remove(Object obj) {
                return !LinkedListMultimap.this.removeAll(obj).isEmpty();
            }

            public int size() {
                return LinkedListMultimap.this.l.size();
            }
        };
    }

    public void clear() {
        this.j = null;
        this.k = null;
        this.l.clear();
        this.m = 0;
        this.n++;
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public boolean containsKey(Object obj) {
        return this.l.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    public final Multiset<K> d() {
        return new Multimaps.Keys(this);
    }

    public final Collection e() {
        return new AbstractSequentialList<Object>() {
            public ListIterator<Object> listIterator(int i) {
                final NodeIterator nodeIterator = new NodeIterator(i);
                return new TransformedListIterator<Map.Entry<Object, Object>, Object>(nodeIterator) {
                    public final Object a(Object obj) {
                        return ((Map.Entry) obj).getValue();
                    }

                    public void set(Object obj) {
                        boolean z;
                        NodeIterator nodeIterator = nodeIterator;
                        if (nodeIterator.g != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        Preconditions.checkState(z);
                        nodeIterator.g.f = obj;
                    }
                };
            }

            public int size() {
                return LinkedListMultimap.this.m;
            }
        };
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final java.util.Iterator<Map.Entry<K, V>> f() {
        throw new AssertionError("should never be called");
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final Node<K, V> i(K k2, V v, Node<K, V> node) {
        Node<K, V> node2 = new Node<>(k2, v);
        if (this.j == null) {
            this.k = node2;
            this.j = node2;
            this.l.put(k2, new KeyList(node2));
            this.n++;
        } else if (node == null) {
            Node<K, V> node3 = this.k;
            node3.g = node2;
            node2.h = node3;
            this.k = node2;
            KeyList keyList = this.l.get(k2);
            if (keyList == null) {
                this.l.put(k2, new KeyList(node2));
                this.n++;
            } else {
                keyList.c++;
                Node<K, V> node4 = keyList.b;
                node4.i = node2;
                node2.j = node4;
                keyList.b = node2;
            }
        } else {
            this.l.get(k2).c++;
            node2.h = node.h;
            node2.j = node.j;
            node2.g = node;
            node2.i = node;
            Node<K, V> node5 = node.j;
            if (node5 == null) {
                this.l.get(k2).a = node2;
            } else {
                node5.i = node2;
            }
            Node<K, V> node6 = node.h;
            if (node6 == null) {
                this.j = node2;
            } else {
                node6.g = node2;
            }
            node.h = node2;
            node.j = node2;
        }
        this.m++;
        return node2;
    }

    public boolean isEmpty() {
        return this.j == null;
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public boolean put(K k2, V v) {
        i(k2, v, (Node) null);
        return true;
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    public int size() {
        return this.m;
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public LinkedListMultimap(int i) {
        this.l = CompactHashMap.createWithExpectedSize(i);
    }

    public static <K, V> LinkedListMultimap<K, V> create(int i) {
        return new LinkedListMultimap<>(i);
    }

    public List<Map.Entry<K, V>> entries() {
        return (List) super.entries();
    }

    public List<V> get(final K k2) {
        return new AbstractSequentialList<V>() {
            public ListIterator<V> listIterator(int i) {
                return new ValueForKeyIterator(k2, i);
            }

            public int size() {
                KeyList keyList = LinkedListMultimap.this.l.get(k2);
                if (keyList == null) {
                    return 0;
                }
                return keyList.c;
            }
        };
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    public List<V> removeAll(Object obj) {
        List<V> unmodifiableList = Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(obj)));
        Iterators.b(new ValueForKeyIterator(obj));
        return unmodifiableList;
    }

    public List<V> replaceValues(K k2, Iterable<? extends V> iterable) {
        List<V> unmodifiableList = Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(k2)));
        ValueForKeyIterator valueForKeyIterator = new ValueForKeyIterator(k2);
        java.util.Iterator<? extends V> it = iterable.iterator();
        while (valueForKeyIterator.hasNext() && it.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.set(it.next());
        }
        while (valueForKeyIterator.hasNext()) {
            valueForKeyIterator.next();
            valueForKeyIterator.remove();
        }
        while (it.hasNext()) {
            valueForKeyIterator.add(it.next());
        }
        return unmodifiableList;
    }

    public List<V> values() {
        return (List) super.values();
    }

    public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedListMultimap<K, V> linkedListMultimap = new LinkedListMultimap<>(multimap.keySet().size());
        linkedListMultimap.putAll(multimap);
        return linkedListMultimap;
    }

    public class ValueForKeyIterator implements ListIterator<V>, j$.util.Iterator {
        public final Object c;
        public int f;
        public Node<K, V> g;
        public Node<K, V> h;
        public Node<K, V> i;

        public ValueForKeyIterator(Object obj) {
            Node<K, V> node;
            this.c = obj;
            KeyList keyList = LinkedListMultimap.this.l.get(obj);
            if (keyList == null) {
                node = null;
            } else {
                node = keyList.a;
            }
            this.g = node;
        }

        public void add(V v) {
            this.i = LinkedListMultimap.this.i(this.c, v, this.g);
            this.f++;
            this.h = null;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.g != null;
        }

        public boolean hasPrevious() {
            return this.i != null;
        }

        public V next() {
            Node<K, V> node = this.g;
            if (node != null) {
                this.h = node;
                this.i = node;
                this.g = node.i;
                this.f++;
                return node.f;
            }
            throw new NoSuchElementException();
        }

        public int nextIndex() {
            return this.f;
        }

        public V previous() {
            Node<K, V> node = this.i;
            if (node != null) {
                this.h = node;
                this.g = node;
                this.i = node.j;
                this.f--;
                return node.f;
            }
            throw new NoSuchElementException();
        }

        public int previousIndex() {
            return this.f - 1;
        }

        public void remove() {
            boolean z;
            if (this.h != null) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.e(z);
            Node<K, V> node = this.h;
            if (node != this.g) {
                this.i = node.j;
                this.f--;
            } else {
                this.g = node.i;
            }
            LinkedListMultimap.h(LinkedListMultimap.this, node);
            this.h = null;
        }

        public void set(V v) {
            boolean z;
            if (this.h != null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z);
            this.h.f = v;
        }

        public ValueForKeyIterator(Object obj, int i2) {
            int i3;
            Node<K, V> node;
            Node<K, V> node2;
            KeyList keyList = LinkedListMultimap.this.l.get(obj);
            if (keyList == null) {
                i3 = 0;
            } else {
                i3 = keyList.c;
            }
            Preconditions.checkPositionIndex(i2, i3);
            if (i2 < i3 / 2) {
                if (keyList == null) {
                    node = null;
                } else {
                    node = keyList.a;
                }
                this.g = node;
                while (true) {
                    int i4 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    next();
                    i2 = i4;
                }
            } else {
                if (keyList == null) {
                    node2 = null;
                } else {
                    node2 = keyList.b;
                }
                this.i = node2;
                this.f = i3;
                while (true) {
                    int i5 = i2 + 1;
                    if (i2 >= i3) {
                        break;
                    }
                    previous();
                    i2 = i5;
                }
            }
            this.c = obj;
            this.h = null;
        }
    }
}
