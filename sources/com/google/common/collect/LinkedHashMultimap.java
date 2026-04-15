package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public final class LinkedHashMultimap<K, V> extends LinkedHashMultimapGwtSerializationDependencies<K, V> {
    private static final long serialVersionUID = 1;
    public transient int l = 2;
    public transient ValueEntry<K, V> m;

    public static final class ValueEntry<K, V> extends ImmutableEntry<K, V> implements ValueSetLink<K, V> {
        public final int g;
        public ValueEntry<K, V> h;
        public ValueSetLink<K, V> i;
        public ValueSetLink<K, V> j;
        public ValueEntry<K, V> k;
        public ValueEntry<K, V> l;

        public ValueEntry(K k2, V v, int i2, ValueEntry<K, V> valueEntry) {
            super(k2, v);
            this.g = i2;
            this.h = valueEntry;
        }

        public final boolean a(int i2, Object obj) {
            return this.g == i2 && Objects.equal(getValue(), obj);
        }

        public ValueEntry<K, V> getPredecessorInMultimap() {
            return this.k;
        }

        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.i;
        }

        public ValueEntry<K, V> getSuccessorInMultimap() {
            return this.l;
        }

        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.j;
        }

        public void setPredecessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.k = valueEntry;
        }

        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.i = valueSetLink;
        }

        public void setSuccessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.l = valueEntry;
        }

        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.j = valueSetLink;
        }
    }

    public final class ValueSet extends Sets.ImprovedAbstractSet<V> implements ValueSetLink<K, V> {
        public final K c;
        public ValueEntry<K, V>[] f;
        public int g = 0;
        public int h = 0;
        public ValueSetLink<K, V> i;
        public ValueSetLink<K, V> j;

        public ValueSet(K k2, int i2) {
            this.c = k2;
            this.i = this;
            this.j = this;
            this.f = new ValueEntry[Hashing.a(i2, 1.0d)];
        }

        public boolean add(V v) {
            int c2 = Hashing.c(v);
            ValueEntry<K, V>[] valueEntryArr = this.f;
            int length = (valueEntryArr.length - 1) & c2;
            ValueEntry<K, V> valueEntry = valueEntryArr[length];
            ValueEntry<K, V> valueEntry2 = valueEntry;
            while (true) {
                boolean z = false;
                if (valueEntry2 == null) {
                    ValueEntry<K, V> valueEntry3 = new ValueEntry<>(this.c, v, c2, valueEntry);
                    ValueSetLink<K, V> valueSetLink = this.j;
                    valueSetLink.setSuccessorInValueSet(valueEntry3);
                    valueEntry3.setPredecessorInValueSet(valueSetLink);
                    valueEntry3.setSuccessorInValueSet(this);
                    setPredecessorInValueSet(valueEntry3);
                    LinkedHashMultimap linkedHashMultimap = LinkedHashMultimap.this;
                    ValueEntry<K, V> predecessorInMultimap = linkedHashMultimap.m.getPredecessorInMultimap();
                    predecessorInMultimap.setSuccessorInMultimap(valueEntry3);
                    valueEntry3.setPredecessorInMultimap(predecessorInMultimap);
                    ValueEntry<K, V> valueEntry4 = linkedHashMultimap.m;
                    valueEntry3.setSuccessorInMultimap(valueEntry4);
                    valueEntry4.setPredecessorInMultimap(valueEntry3);
                    ValueEntry<K, V>[] valueEntryArr2 = this.f;
                    valueEntryArr2[length] = valueEntry3;
                    int i2 = this.g + 1;
                    this.g = i2;
                    this.h++;
                    int length2 = valueEntryArr2.length;
                    if (((double) i2) > ((double) length2) * 1.0d && length2 < 1073741824) {
                        z = true;
                    }
                    if (z) {
                        int length3 = valueEntryArr2.length * 2;
                        ValueEntry<K, V>[] valueEntryArr3 = new ValueEntry[length3];
                        this.f = valueEntryArr3;
                        int i3 = length3 - 1;
                        for (ValueSetLink valueSetLink2 = this.i; valueSetLink2 != this; valueSetLink2 = valueSetLink2.getSuccessorInValueSet()) {
                            ValueEntry<K, V> valueEntry5 = (ValueEntry) valueSetLink2;
                            int i4 = valueEntry5.g & i3;
                            valueEntry5.h = valueEntryArr3[i4];
                            valueEntryArr3[i4] = valueEntry5;
                        }
                    }
                    return true;
                } else if (valueEntry2.a(c2, v)) {
                    return false;
                } else {
                    valueEntry2 = valueEntry2.h;
                }
            }
        }

        public void clear() {
            Arrays.fill(this.f, (Object) null);
            this.g = 0;
            for (ValueSetLink<K, V> valueSetLink = this.i; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                ValueEntry valueEntry = (ValueEntry) valueSetLink;
                ValueEntry predecessorInMultimap = valueEntry.getPredecessorInMultimap();
                ValueEntry successorInMultimap = valueEntry.getSuccessorInMultimap();
                predecessorInMultimap.setSuccessorInMultimap(successorInMultimap);
                successorInMultimap.setPredecessorInMultimap(predecessorInMultimap);
            }
            setSuccessorInValueSet(this);
            setPredecessorInValueSet(this);
            this.h++;
        }

        public boolean contains(Object obj) {
            int c2 = Hashing.c(obj);
            ValueEntry<K, V>[] valueEntryArr = this.f;
            for (ValueEntry<K, V> valueEntry = valueEntryArr[(valueEntryArr.length - 1) & c2]; valueEntry != null; valueEntry = valueEntry.h) {
                if (valueEntry.a(c2, obj)) {
                    return true;
                }
            }
            return false;
        }

        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.j;
        }

        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.i;
        }

        public Iterator<V> iterator() {
            return new Object() {
                public ValueSetLink<K, V> c;
                public ValueEntry<K, V> f;
                public int g;

                {
                    this.c = ValueSet.this.i;
                    this.g = ValueSet.this.h;
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    ValueSet valueSet = ValueSet.this;
                    if (valueSet.h != this.g) {
                        throw new ConcurrentModificationException();
                    } else if (this.c != valueSet) {
                        return true;
                    } else {
                        return false;
                    }
                }

                public V next() {
                    if (hasNext()) {
                        ValueEntry<K, V> valueEntry = (ValueEntry) this.c;
                        V value = valueEntry.getValue();
                        this.f = valueEntry;
                        this.c = valueEntry.getSuccessorInValueSet();
                        return value;
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    boolean z;
                    ValueSet valueSet = ValueSet.this;
                    if (valueSet.h == this.g) {
                        if (this.f != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        CollectPreconditions.e(z);
                        valueSet.remove(this.f.getValue());
                        this.g = valueSet.h;
                        this.f = null;
                        return;
                    }
                    throw new ConcurrentModificationException();
                }
            };
        }

        public boolean remove(Object obj) {
            int c2 = Hashing.c(obj);
            ValueEntry<K, V>[] valueEntryArr = this.f;
            int length = (valueEntryArr.length - 1) & c2;
            ValueEntry<K, V> valueEntry = null;
            for (ValueEntry<K, V> valueEntry2 = valueEntryArr[length]; valueEntry2 != null; valueEntry2 = valueEntry2.h) {
                if (valueEntry2.a(c2, obj)) {
                    if (valueEntry == null) {
                        this.f[length] = valueEntry2.h;
                    } else {
                        valueEntry.h = valueEntry2.h;
                    }
                    ValueSetLink<K, V> predecessorInValueSet = valueEntry2.getPredecessorInValueSet();
                    ValueSetLink<K, V> successorInValueSet = valueEntry2.getSuccessorInValueSet();
                    predecessorInValueSet.setSuccessorInValueSet(successorInValueSet);
                    successorInValueSet.setPredecessorInValueSet(predecessorInValueSet);
                    ValueEntry<K, V> predecessorInMultimap = valueEntry2.getPredecessorInMultimap();
                    ValueEntry<K, V> successorInMultimap = valueEntry2.getSuccessorInMultimap();
                    predecessorInMultimap.setSuccessorInMultimap(successorInMultimap);
                    successorInMultimap.setPredecessorInMultimap(predecessorInMultimap);
                    this.g--;
                    this.h++;
                    return true;
                }
                valueEntry = valueEntry2;
            }
            return false;
        }

        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.j = valueSetLink;
        }

        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.i = valueSetLink;
        }

        public int size() {
            return this.g;
        }
    }

    public interface ValueSetLink<K, V> {
        ValueSetLink<K, V> getPredecessorInValueSet();

        ValueSetLink<K, V> getSuccessorInValueSet();

        void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink);

        void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink);
    }

    public LinkedHashMultimap(int i, int i2) {
        super(CompactLinkedHashMap.createWithExpectedSize(i));
        CollectPreconditions.b(i2, "expectedValuesPerKey");
        this.l = i2;
        ValueEntry<K, V> valueEntry = new ValueEntry<>(null, null, 0, (ValueEntry) null);
        this.m = valueEntry;
        valueEntry.setSuccessorInMultimap(valueEntry);
        valueEntry.setPredecessorInMultimap(valueEntry);
    }

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<>(16, 2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        ValueEntry<K, V> valueEntry = new ValueEntry<>(null, null, 0, (ValueEntry) null);
        this.m = valueEntry;
        valueEntry.setSuccessorInMultimap(valueEntry);
        valueEntry.setPredecessorInMultimap(valueEntry);
        this.l = 2;
        int readInt = objectInputStream.readInt();
        CompactLinkedHashMap createWithExpectedSize = CompactLinkedHashMap.createWithExpectedSize(12);
        for (int i = 0; i < readInt; i++) {
            Object readObject = objectInputStream.readObject();
            createWithExpectedSize.put(readObject, i(readObject));
        }
        int readInt2 = objectInputStream.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            Object readObject2 = objectInputStream.readObject();
            ((Collection) createWithExpectedSize.get(readObject2)).add(objectInputStream.readObject());
        }
        m(createWithExpectedSize);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(keySet().size());
        for (Object writeObject : keySet()) {
            objectOutputStream.writeObject(writeObject);
        }
        objectOutputStream.writeInt(size());
        for (Map.Entry entry : entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    public /* bridge */ /* synthetic */ Map asMap() {
        return super.asMap();
    }

    public void clear() {
        super.clear();
        ValueEntry<K, V> valueEntry = this.m;
        valueEntry.setSuccessorInMultimap(valueEntry);
        valueEntry.setPredecessorInMultimap(valueEntry);
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsKey(Object obj) {
        return super.containsKey(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final java.util.Iterator<Map.Entry<K, V>> f() {
        return new Object() {
            public ValueEntry<K, V> c;
            public ValueEntry<K, V> f;

            {
                this.c = LinkedHashMultimap.this.m.l;
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                if (this.c != LinkedHashMultimap.this.m) {
                    return true;
                }
                return false;
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                LinkedHashMultimap.this.remove(this.f.getKey(), this.f.getValue());
                this.f = null;
            }

            public Map.Entry<K, V> next() {
                if (hasNext()) {
                    ValueEntry<K, V> valueEntry = this.c;
                    this.f = valueEntry;
                    this.c = valueEntry.l;
                    return valueEntry;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public final java.util.Iterator<V> g() {
        return new TransformedIterator<Map.Entry<Object, Object>, Object>(new Object() {
            public ValueEntry<K, V> c;
            public ValueEntry<K, V> f;

            {
                this.c = LinkedHashMultimap.this.m.l;
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                if (this.c != LinkedHashMultimap.this.m) {
                    return true;
                }
                return false;
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                LinkedHashMultimap.this.remove(this.f.getKey(), this.f.getValue());
                this.f = null;
            }

            public Map.Entry<K, V> next() {
                if (hasNext()) {
                    ValueEntry<K, V> valueEntry = this.c;
                    this.f = valueEntry;
                    this.c = valueEntry.l;
                    return valueEntry;
                }
                throw new NoSuchElementException();
            }
        }) {
            public final Object a(Object obj) {
                return ((Map.Entry) obj).getValue();
            }
        };
    }

    public /* bridge */ /* synthetic */ Set get(Object obj) {
        return super.get(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final Collection<V> i(K k) {
        return new ValueSet(k, this.l);
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public Set<K> keySet() {
        return super.keySet();
    }

    public /* bridge */ /* synthetic */ Multiset keys() {
        return super.keys();
    }

    public final Set<V> p() {
        return CompactLinkedHashSet.createWithExpectedSize(this.l);
    }

    public /* bridge */ /* synthetic */ boolean put(Object obj, Object obj2) {
        return super.put(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean putAll(Multimap multimap) {
        return super.putAll(multimap);
    }

    public /* bridge */ /* synthetic */ boolean remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    public /* bridge */ /* synthetic */ Set removeAll(Object obj) {
        return super.removeAll(obj);
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public Collection<V> values() {
        return super.values();
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int i, int i2) {
        return new LinkedHashMultimap<>(Maps.c(i), Maps.c(i2));
    }

    public Set<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    public /* bridge */ /* synthetic */ boolean putAll(Object obj, Iterable iterable) {
        return super.putAll(obj, iterable);
    }

    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return super.replaceValues((Object) k, (Iterable) iterable);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> create = create(multimap.keySet().size(), 2);
        create.putAll(multimap);
        return create;
    }
}
