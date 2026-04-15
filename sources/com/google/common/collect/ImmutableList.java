package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
    public static final UnmodifiableListIterator<Object> f = new Itr(RegularImmutableList.i, 0);

    public static final class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            super(4);
        }

        public Builder(int i) {
            super(i);
        }

        public ImmutableList<E> build() {
            this.c = true;
            return ImmutableList.g(this.b, this.a);
        }

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll(iterable);
            return this;
        }

        public Builder<E> add(E e) {
            super.add(e);
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll(it);
            return this;
        }

        public Builder<E> add(E... eArr) {
            super.add(eArr);
            return this;
        }
    }

    public static class Itr<E> extends AbstractIndexedListIterator<E> {
        public final ImmutableList<E> g;

        public Itr(ImmutableList<E> immutableList, int i) {
            super(immutableList.size(), i);
            this.g = immutableList;
        }

        public final E get(int i) {
            return this.g.get(i);
        }
    }

    public static class ReverseImmutableList<E> extends ImmutableList<E> {
        public final transient ImmutableList<E> g;

        public ReverseImmutableList(ImmutableList<E> immutableList) {
            this.g = immutableList;
        }

        public boolean contains(Object obj) {
            return this.g.contains(obj);
        }

        public E get(int i) {
            Preconditions.checkElementIndex(i, size());
            return this.g.get((size() - 1) - i);
        }

        public int indexOf(Object obj) {
            int lastIndexOf = this.g.lastIndexOf(obj);
            if (lastIndexOf >= 0) {
                return (size() - 1) - lastIndexOf;
            }
            return -1;
        }

        public final boolean isPartialView() {
            return this.g.isPartialView();
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return ImmutableList.super.iterator();
        }

        public int lastIndexOf(Object obj) {
            int indexOf = this.g.indexOf(obj);
            if (indexOf >= 0) {
                return (size() - 1) - indexOf;
            }
            return -1;
        }

        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return ImmutableList.super.listIterator();
        }

        public ImmutableList<E> reverse() {
            return this.g;
        }

        public int size() {
            return this.g.size();
        }

        public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
            return ImmutableList.super.listIterator(i);
        }

        public ImmutableList<E> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return this.g.subList(size() - i2, size() - i).reverse();
        }
    }

    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object[] c;

        public SerializedForm(Object[] objArr) {
            this.c = objArr;
        }

        public Object readResolve() {
            return ImmutableList.copyOf((E[]) this.c);
        }
    }

    public class SubList extends ImmutableList<E> {
        public final transient int g;
        public final transient int h;

        public SubList(int i2, int i3) {
            this.g = i2;
            this.h = i3;
        }

        public final Object[] b() {
            return ImmutableList.this.b();
        }

        public final int c() {
            return ImmutableList.this.f() + this.g + this.h;
        }

        public final int f() {
            return ImmutableList.this.f() + this.g;
        }

        public E get(int i2) {
            Preconditions.checkElementIndex(i2, this.h);
            return ImmutableList.this.get(i2 + this.g);
        }

        public final boolean isPartialView() {
            return true;
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return ImmutableList.super.iterator();
        }

        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return ImmutableList.super.listIterator();
        }

        public int size() {
            return this.h;
        }

        public /* bridge */ /* synthetic */ ListIterator listIterator(int i2) {
            return ImmutableList.super.listIterator(i2);
        }

        public ImmutableList<E> subList(int i2, int i3) {
            Preconditions.checkPositionIndexes(i2, i3, this.h);
            int i4 = this.g;
            return ImmutableList.this.subList(i2 + i4, i3 + i4);
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static <E> Builder<E> builderWithExpectedSize(int i) {
        CollectPreconditions.b(i, "expectedSize");
        return new Builder<>(i);
    }

    public static <E> ImmutableList<E> copyOf(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    public static ImmutableList g(int i, Object[] objArr) {
        if (i == 0) {
            return of();
        }
        return new RegularImmutableList(objArr, i);
    }

    public static <E> ImmutableList<E> of() {
        return RegularImmutableList.i;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Iterable<? extends E>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <E> com.google.common.collect.ImmutableList<E> sortedCopyOf(java.util.Comparator<? super E> r1, java.lang.Iterable<? extends E> r2) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r1)
            boolean r0 = r2 instanceof java.util.Collection
            if (r0 == 0) goto L_0x000a
            java.util.Collection r2 = (java.util.Collection) r2
            goto L_0x0012
        L_0x000a:
            java.util.Iterator r2 = r2.iterator()
            java.util.ArrayList r2 = com.google.common.collect.Lists.newArrayList(r2)
        L_0x0012:
            java.lang.Object[] r2 = r2.toArray()
            int r0 = r2.length
            com.google.common.collect.ObjectArrays.a(r0, r2)
            java.util.Arrays.sort(r2, r1)
            int r1 = r2.length
            com.google.common.collect.ImmutableList r1 = g(r1, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableList.sortedCopyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableList");
    }

    public int a(int i, Object[] objArr) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2);
        }
        return i + size;
    }

    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public final ImmutableList<E> asList() {
        return this;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public boolean equals(Object obj) {
        if (obj == Preconditions.checkNotNull(this)) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (!(list instanceof RandomAccess)) {
                    return Iterators.elementsEqual(iterator(), list.iterator());
                }
                int i = 0;
                while (i < size) {
                    if (Objects.equal(get(i), list.get(i))) {
                        i++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = ~(~(get(i2).hashCode() + (i * 31)));
        }
        return i;
    }

    public int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (obj.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int size = size() - 1; size >= 0; size--) {
            if (obj.equals(get(size))) {
                return size;
            }
        }
        return -1;
    }

    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> reverse() {
        return size() <= 1 ? this : new ReverseImmutableList(this);
    }

    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    public Object writeReplace() {
        return new SerializedForm(toArray());
    }

    @SafeVarargs
    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E... eArr) {
        E[] eArr2 = eArr;
        Preconditions.checkArgument(eArr2.length <= 2147483635, "the total number of elements must fit in an int");
        int length = eArr2.length + 12;
        Object[] objArr = new Object[length];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        objArr[3] = e4;
        objArr[4] = e5;
        objArr[5] = e6;
        objArr[6] = e7;
        objArr[7] = e8;
        objArr[8] = e9;
        objArr[9] = e10;
        objArr[10] = e11;
        objArr[11] = e12;
        System.arraycopy(eArr2, 0, objArr, 12, eArr2.length);
        ObjectArrays.a(length, objArr);
        return g(length, objArr);
    }

    public UnmodifiableIterator<E> iterator() {
        return listIterator();
    }

    public ImmutableList<E> subList(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return of();
        }
        return new SubList(i, i3);
    }

    public UnmodifiableListIterator<E> listIterator() {
        return listIterator(0);
    }

    public UnmodifiableListIterator<E> listIterator(int i) {
        Preconditions.checkPositionIndex(i, size());
        if (isEmpty()) {
            return f;
        }
        return new Itr(this, i);
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> collection) {
        if (collection instanceof ImmutableCollection) {
            ImmutableList<E> asList = ((ImmutableCollection) collection).asList();
            if (!asList.isPartialView()) {
                return asList;
            }
            Object[] array = asList.toArray();
            return g(array.length, array);
        }
        Object[] array2 = collection.toArray();
        ObjectArrays.a(array2.length, array2);
        return g(array2.length, array2);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Iterable<? extends E>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <E extends java.lang.Comparable<? super E>> com.google.common.collect.ImmutableList<E> sortedCopyOf(java.lang.Iterable<? extends E> r2) {
        /*
            r0 = 0
            java.lang.Comparable[] r0 = new java.lang.Comparable[r0]
            boolean r1 = r2 instanceof java.util.Collection
            if (r1 == 0) goto L_0x000a
            java.util.Collection r2 = (java.util.Collection) r2
            goto L_0x0012
        L_0x000a:
            java.util.Iterator r2 = r2.iterator()
            java.util.ArrayList r2 = com.google.common.collect.Lists.newArrayList(r2)
        L_0x0012:
            java.lang.Object[] r2 = r2.toArray(r0)
            java.lang.Comparable[] r2 = (java.lang.Comparable[]) r2
            int r0 = r2.length
            com.google.common.collect.ObjectArrays.a(r0, r2)
            java.util.Arrays.sort(r2)
            int r0 = r2.length
            com.google.common.collect.ImmutableList r2 = g(r0, r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableList.sortedCopyOf(java.lang.Iterable):com.google.common.collect.ImmutableList");
    }

    public static <E> ImmutableList<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of();
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return of(next);
        }
        return new Builder().add((Object) next).addAll((Iterator) it).build();
    }

    public static <E> ImmutableList<E> copyOf(E[] eArr) {
        if (eArr.length == 0) {
            return of();
        }
        Object[] objArr = (Object[]) eArr.clone();
        ObjectArrays.a(objArr.length, objArr);
        return g(objArr.length, objArr);
    }

    public static <E> ImmutableList<E> of(E e) {
        Object[] objArr = {e};
        ObjectArrays.a(1, objArr);
        return g(1, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2) {
        Object[] objArr = {e, e2};
        ObjectArrays.a(2, objArr);
        return g(2, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3) {
        Object[] objArr = {e, e2, e3};
        ObjectArrays.a(3, objArr);
        return g(3, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4) {
        Object[] objArr = {e, e2, e3, e4};
        ObjectArrays.a(4, objArr);
        return g(4, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5) {
        Object[] objArr = {e, e2, e3, e4, e5};
        ObjectArrays.a(5, objArr);
        return g(5, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6) {
        Object[] objArr = {e, e2, e3, e4, e5, e6};
        ObjectArrays.a(6, objArr);
        return g(6, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7) {
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7};
        ObjectArrays.a(7, objArr);
        return g(7, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7, e8};
        ObjectArrays.a(8, objArr);
        return g(8, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7, e8, e9};
        ObjectArrays.a(9, objArr);
        return g(9, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7, e8, e9, e10};
        ObjectArrays.a(10, objArr);
        return g(10, objArr);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
        Object[] objArr = {e, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11};
        ObjectArrays.a(11, objArr);
        return g(11, objArr);
    }
}
