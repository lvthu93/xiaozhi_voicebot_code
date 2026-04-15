package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectCountHashMap;
import java.io.Serializable;

class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    public static final RegularImmutableMultiset<Object> l = new RegularImmutableMultiset<>(ObjectCountHashMap.create());
    public final transient ObjectCountHashMap<E> i;
    public final transient int j;
    public transient ImmutableSet<E> k;

    public final class ElementSet extends IndexedImmutableSet<E> {
        public ElementSet() {
        }

        public boolean contains(Object obj) {
            return RegularImmutableMultiset.this.contains(obj);
        }

        public final E get(int i) {
            return RegularImmutableMultiset.this.i.c(i);
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return RegularImmutableMultiset.this.i.c;
        }
    }

    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object[] c;
        public final int[] f;

        public SerializedForm(Multiset<?> multiset) {
            int size = multiset.entrySet().size();
            this.c = new Object[size];
            this.f = new int[size];
            int i = 0;
            for (Multiset.Entry next : multiset.entrySet()) {
                this.c[i] = next.getElement();
                this.f[i] = next.getCount();
                i++;
            }
        }

        public Object readResolve() {
            Object[] objArr = this.c;
            ImmutableMultiset.Builder builder = new ImmutableMultiset.Builder(objArr.length);
            for (int i = 0; i < objArr.length; i++) {
                builder.addCopies(objArr[i], this.f[i]);
            }
            return builder.build();
        }
    }

    public RegularImmutableMultiset(ObjectCountHashMap<E> objectCountHashMap) {
        this.i = objectCountHashMap;
        long j2 = 0;
        for (int i2 = 0; i2 < objectCountHashMap.c; i2++) {
            j2 += (long) objectCountHashMap.d(i2);
        }
        this.j = y3.b(j2);
    }

    public int count(Object obj) {
        return this.i.get(obj);
    }

    public final boolean isPartialView() {
        return false;
    }

    public final Multiset.Entry<E> j(int i2) {
        ObjectCountHashMap<E> objectCountHashMap = this.i;
        Preconditions.checkElementIndex(i2, objectCountHashMap.c);
        return new ObjectCountHashMap.MapEntry(i2);
    }

    public int size() {
        return this.j;
    }

    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> immutableSet = this.k;
        if (immutableSet != null) {
            return immutableSet;
        }
        ElementSet elementSet = new ElementSet();
        this.k = elementSet;
        return elementSet;
    }
}
