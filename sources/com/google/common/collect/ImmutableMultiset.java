package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Multiset;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public abstract class ImmutableMultiset<E> extends ImmutableMultisetGwtSerializationDependencies<E> implements Multiset<E> {
    public static final /* synthetic */ int h = 0;
    public transient ImmutableList<E> f;
    public transient ImmutableSet<Multiset.Entry<E>> g;

    public static class Builder<E> extends ImmutableCollection.Builder<E> {
        public ObjectCountHashMap<E> a;
        public boolean b;
        public boolean c;

        public Builder() {
            this(4);
        }

        public Builder<E> addCopies(E e, int i) {
            if (i == 0) {
                return this;
            }
            if (this.b) {
                this.a = new ObjectCountHashMap<>(this.a);
                this.c = false;
            }
            this.b = false;
            Preconditions.checkNotNull(e);
            ObjectCountHashMap<E> objectCountHashMap = this.a;
            objectCountHashMap.put(e, i + objectCountHashMap.get(e));
            return this;
        }

        public Builder<E> setCount(E e, int i) {
            if (i == 0 && !this.c) {
                this.a = new ObjectCountLinkedHashMap(this.a);
                this.c = true;
            } else if (this.b) {
                this.a = new ObjectCountHashMap<>(this.a);
                this.c = false;
            }
            this.b = false;
            Preconditions.checkNotNull(e);
            if (i == 0) {
                this.a.remove(e);
            } else {
                this.a.put(Preconditions.checkNotNull(e), i);
            }
            return this;
        }

        public Builder(int i) {
            this.b = false;
            this.c = false;
            this.a = ObjectCountHashMap.createWithExpectedSize(i);
        }

        public ImmutableMultiset<E> build() {
            ObjectCountHashMap<E> objectCountHashMap = this.a;
            if (objectCountHashMap.c == 0) {
                return ImmutableMultiset.of();
            }
            if (this.c) {
                this.a = new ObjectCountHashMap<>(objectCountHashMap);
                this.c = false;
            }
            this.b = true;
            return new RegularImmutableMultiset(this.a);
        }

        public Builder<E> add(E e) {
            return addCopies(e, 1);
        }

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            ObjectCountHashMap<E> objectCountHashMap;
            if (iterable instanceof Multiset) {
                Multiset multiset = (Multiset) iterable;
                if (multiset instanceof RegularImmutableMultiset) {
                    objectCountHashMap = ((RegularImmutableMultiset) multiset).i;
                } else {
                    objectCountHashMap = multiset instanceof AbstractMapBasedMultiset ? ((AbstractMapBasedMultiset) multiset).g : null;
                }
                if (objectCountHashMap != null) {
                    ObjectCountHashMap<E> objectCountHashMap2 = this.a;
                    objectCountHashMap2.a(Math.max(objectCountHashMap2.c, objectCountHashMap.c));
                    for (int b2 = objectCountHashMap.b(); b2 >= 0; b2 = objectCountHashMap.i(b2)) {
                        addCopies(objectCountHashMap.c(b2), objectCountHashMap.d(b2));
                    }
                } else {
                    Set entrySet = multiset.entrySet();
                    ObjectCountHashMap<E> objectCountHashMap3 = this.a;
                    objectCountHashMap3.a(Math.max(objectCountHashMap3.c, entrySet.size()));
                    for (Multiset.Entry entry : multiset.entrySet()) {
                        addCopies(entry.getElement(), entry.getCount());
                    }
                }
            } else {
                super.addAll(iterable);
            }
            return this;
        }

        public Builder<E> add(E... eArr) {
            super.add(eArr);
            return this;
        }

        public Builder(Object obj) {
            this.b = false;
            this.c = false;
            this.a = null;
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll(it);
            return this;
        }
    }

    public final class EntrySet extends IndexedImmutableSet<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0;

        public EntrySet() {
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            if (entry.getCount() > 0 && ImmutableMultiset.this.count(entry.getElement()) == entry.getCount()) {
                return true;
            }
            return false;
        }

        public final Object get(int i) {
            return ImmutableMultiset.this.j(i);
        }

        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        public final boolean isPartialView() {
            return ImmutableMultiset.this.isPartialView();
        }

        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        public Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }
    }

    public static class EntrySetSerializedForm<E> implements Serializable {
        public final ImmutableMultiset<E> c;

        public EntrySetSerializedForm(ImmutableMultiset<E> immutableMultiset) {
            this.c = immutableMultiset;
        }

        public Object readResolve() {
            return this.c.entrySet();
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] eArr) {
        return g(eArr);
    }

    public static <E> ImmutableMultiset<E> g(E... eArr) {
        return new Builder().add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> of() {
        return RegularImmutableMultiset.l;
    }

    public final int a(int i, Object[] objArr) {
        UnmodifiableIterator it = entrySet().iterator();
        while (it.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry) it.next();
            Arrays.fill(objArr, i, entry.getCount() + i, entry.getElement());
            i += entry.getCount();
        }
        return i;
    }

    @Deprecated
    public final int add(E e, int i) {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.f;
        if (immutableList != null) {
            return immutableList;
        }
        ImmutableList<E> asList = super.asList();
        this.f = asList;
        return asList;
    }

    public boolean contains(Object obj) {
        return count(obj) > 0;
    }

    public abstract ImmutableSet<E> elementSet();

    public boolean equals(Object obj) {
        return Multisets.a(this, obj);
    }

    public int hashCode() {
        return Sets.b(entrySet());
    }

    public abstract Multiset.Entry<E> j(int i);

    @Deprecated
    public final int remove(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final int setCount(E e, int i) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return entrySet().toString();
    }

    /* access modifiers changed from: package-private */
    public abstract Object writeReplace();

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableMultiset) {
            ImmutableMultiset<E> immutableMultiset = (ImmutableMultiset) iterable;
            if (!immutableMultiset.isPartialView()) {
                return immutableMultiset;
            }
        }
        Builder builder = new Builder(Multisets.b(iterable));
        builder.addAll((Iterable) iterable);
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> of(E e) {
        return g(e);
    }

    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<E>> immutableSet = this.g;
        if (immutableSet == null) {
            immutableSet = isEmpty() ? ImmutableSet.of() : new EntrySet();
            this.g = immutableSet;
        }
        return immutableSet;
    }

    public UnmodifiableIterator<E> iterator() {
        final UnmodifiableIterator it = entrySet().iterator();
        return new UnmodifiableIterator<E>() {
            public int c;
            public E f;

            public boolean hasNext() {
                return this.c > 0 || it.hasNext();
            }

            public E next() {
                if (this.c <= 0) {
                    Multiset.Entry entry = (Multiset.Entry) it.next();
                    this.f = entry.getElement();
                    this.c = entry.getCount();
                }
                this.c--;
                return this.f;
            }
        };
    }

    @Deprecated
    public final boolean setCount(E e, int i, int i2) {
        throw new UnsupportedOperationException();
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2) {
        return g(e, e2);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3) {
        return g(e, e2, e3);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4) {
        return g(e, e2, e3, e4);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return g(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        return new Builder().add((Object) e).add((Object) e2).add((Object) e3).add((Object) e4).add((Object) e5).add((Object) e6).add((Object[]) eArr).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> it) {
        return new Builder().addAll((Iterator) it).build();
    }
}
