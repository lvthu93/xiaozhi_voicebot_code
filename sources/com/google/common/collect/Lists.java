package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import j$.util.Iterator;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class Lists {

    /* renamed from: com.google.common.collect.Lists$1  reason: invalid class name */
    final class AnonymousClass1 extends RandomAccessListWrapper<Object> {
        public ListIterator<Object> listIterator(int i) {
            throw null;
        }
    }

    /* renamed from: com.google.common.collect.Lists$2  reason: invalid class name */
    final class AnonymousClass2 extends AbstractListWrapper<Object> {
        public ListIterator<Object> listIterator(int i) {
            throw null;
        }
    }

    public static class AbstractListWrapper<E> extends AbstractList<E> {
        public void add(int i, E e) {
            throw null;
        }

        public boolean addAll(int i, Collection<? extends E> collection) {
            throw null;
        }

        public boolean contains(Object obj) {
            throw null;
        }

        public E get(int i) {
            throw null;
        }

        public E remove(int i) {
            throw null;
        }

        public E set(int i, E e) {
            throw null;
        }

        public int size() {
            throw null;
        }
    }

    public static final class CharSequenceAsList extends AbstractList<Character> {
        public final CharSequence c;

        public CharSequenceAsList(CharSequence charSequence) {
            this.c = charSequence;
        }

        public int size() {
            return this.c.length();
        }

        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.c.charAt(i));
        }
    }

    public static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        public final E c;
        public final E[] f;

        public OnePlusArrayList(E e, E[] eArr) {
            this.c = e;
            this.f = (Object[]) Preconditions.checkNotNull(eArr);
        }

        public E get(int i) {
            Preconditions.checkElementIndex(i, size());
            if (i == 0) {
                return this.c;
            }
            return this.f[i - 1];
        }

        public int size() {
            return y3.b(((long) this.f.length) + ((long) 1));
        }
    }

    public static class Partition<T> extends AbstractList<List<T>> {
        public final List<T> c;
        public final int f;

        public Partition(List<T> list, int i) {
            this.c = list;
            this.f = i;
        }

        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
            if ((r1 & r2) != false) goto L_0x0061;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
            if (r4 > 0) goto L_0x0061;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0054, code lost:
            if (r0 > 0) goto L_0x0061;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0057, code lost:
            if (r0 < 0) goto L_0x0061;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int size() {
            /*
                r8 = this;
                java.util.List<T> r0 = r8.c
                int r0 = r0.size()
                java.math.RoundingMode r1 = java.math.RoundingMode.CEILING
                com.google.common.base.Preconditions.checkNotNull(r1)
                int r2 = r8.f
                if (r2 == 0) goto L_0x006d
                int r3 = r0 / r2
                int r4 = r2 * r3
                int r4 = r0 - r4
                if (r4 != 0) goto L_0x0018
                goto L_0x0064
            L_0x0018:
                r0 = r0 ^ r2
                int r0 = r0 >> 31
                r5 = 1
                r0 = r0 | r5
                int[] r6 = defpackage.w3.a.a
                int r7 = r1.ordinal()
                r6 = r6[r7]
                r7 = 0
                switch(r6) {
                    case 1: goto L_0x005a;
                    case 2: goto L_0x0060;
                    case 3: goto L_0x0057;
                    case 4: goto L_0x0061;
                    case 5: goto L_0x0054;
                    case 6: goto L_0x002f;
                    case 7: goto L_0x002f;
                    case 8: goto L_0x002f;
                    default: goto L_0x0029;
                }
            L_0x0029:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            L_0x002f:
                int r4 = java.lang.Math.abs(r4)
                int r2 = java.lang.Math.abs(r2)
                int r2 = r2 - r4
                int r4 = r4 - r2
                if (r4 != 0) goto L_0x0051
                java.math.RoundingMode r2 = java.math.RoundingMode.HALF_UP
                if (r1 == r2) goto L_0x0061
                java.math.RoundingMode r2 = java.math.RoundingMode.HALF_EVEN
                if (r1 != r2) goto L_0x0045
                r1 = 1
                goto L_0x0046
            L_0x0045:
                r1 = 0
            L_0x0046:
                r2 = r3 & 1
                if (r2 == 0) goto L_0x004c
                r2 = 1
                goto L_0x004d
            L_0x004c:
                r2 = 0
            L_0x004d:
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0060
                goto L_0x0061
            L_0x0051:
                if (r4 <= 0) goto L_0x0060
                goto L_0x0061
            L_0x0054:
                if (r0 <= 0) goto L_0x0060
                goto L_0x0061
            L_0x0057:
                if (r0 >= 0) goto L_0x0060
                goto L_0x0061
            L_0x005a:
                if (r4 != 0) goto L_0x005d
                goto L_0x005e
            L_0x005d:
                r5 = 0
            L_0x005e:
                if (r5 == 0) goto L_0x0065
            L_0x0060:
                r5 = 0
            L_0x0061:
                if (r5 == 0) goto L_0x0064
                int r3 = r3 + r0
            L_0x0064:
                return r3
            L_0x0065:
                java.lang.ArithmeticException r0 = new java.lang.ArithmeticException
                java.lang.String r1 = "mode was UNNECESSARY, but rounding was necessary"
                r0.<init>(r1)
                throw r0
            L_0x006d:
                java.lang.ArithmeticException r0 = new java.lang.ArithmeticException
                java.lang.String r1 = "/ by zero"
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Lists.Partition.size():int");
        }

        public List<T> get(int i) {
            Preconditions.checkElementIndex(i, size());
            int i2 = this.f;
            int i3 = i * i2;
            List<T> list = this.c;
            return list.subList(i3, Math.min(i2 + i3, list.size()));
        }
    }

    public static class RandomAccessListWrapper<E> extends AbstractListWrapper<E> implements RandomAccess {
    }

    public static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
        public RandomAccessPartition(List<T> list, int i) {
            super(list, i);
        }
    }

    public static class RandomAccessReverseList<T> extends ReverseList<T> implements RandomAccess {
        public RandomAccessReverseList(List<T> list) {
            super(list);
        }
    }

    public static class ReverseList<T> extends AbstractList<T> {
        public final List<T> c;

        public ReverseList(List<T> list) {
            this.c = (List) Preconditions.checkNotNull(list);
        }

        public static int a(ReverseList reverseList, int i) {
            int size = reverseList.size();
            Preconditions.checkPositionIndex(i, size);
            return size - i;
        }

        public void add(int i, T t) {
            int size = size();
            Preconditions.checkPositionIndex(i, size);
            this.c.add(size - i, t);
        }

        public void clear() {
            this.c.clear();
        }

        public T get(int i) {
            int size = size();
            Preconditions.checkElementIndex(i, size);
            return this.c.get((size - 1) - i);
        }

        public Iterator<T> iterator() {
            return listIterator();
        }

        public ListIterator<T> listIterator(int i) {
            int size = size();
            Preconditions.checkPositionIndex(i, size);
            final ListIterator<T> listIterator = this.c.listIterator(size - i);
            return new Object() {
                public boolean c;

                public void add(T t) {
                    ListIterator listIterator = listIterator;
                    listIterator.add(t);
                    listIterator.previous();
                    this.c = false;
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return listIterator.hasPrevious();
                }

                public boolean hasPrevious() {
                    return listIterator.hasNext();
                }

                public T next() {
                    if (hasNext()) {
                        this.c = true;
                        return listIterator.previous();
                    }
                    throw new NoSuchElementException();
                }

                public int nextIndex() {
                    return ReverseList.a(ReverseList.this, listIterator.nextIndex());
                }

                public T previous() {
                    if (hasPrevious()) {
                        this.c = true;
                        return listIterator.next();
                    }
                    throw new NoSuchElementException();
                }

                public int previousIndex() {
                    return nextIndex() - 1;
                }

                public void remove() {
                    CollectPreconditions.e(this.c);
                    listIterator.remove();
                    this.c = false;
                }

                public void set(T t) {
                    Preconditions.checkState(this.c);
                    listIterator.set(t);
                }
            };
        }

        public T remove(int i) {
            int size = size();
            Preconditions.checkElementIndex(i, size);
            return this.c.remove((size - 1) - i);
        }

        public final void removeRange(int i, int i2) {
            subList(i, i2).clear();
        }

        public T set(int i, T t) {
            int size = size();
            Preconditions.checkElementIndex(i, size);
            return this.c.set((size - 1) - i, t);
        }

        public int size() {
            return this.c.size();
        }

        public List<T> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            int size = size();
            Preconditions.checkPositionIndex(i2, size);
            int i3 = size - i2;
            int size2 = size();
            Preconditions.checkPositionIndex(i, size2);
            return Lists.reverse(this.c.subList(i3, size2 - i));
        }
    }

    public static final class StringAsImmutableList extends ImmutableList<Character> {
        public final String g;

        public StringAsImmutableList(String str) {
            this.g = str;
        }

        public int indexOf(Object obj) {
            if (!(obj instanceof Character)) {
                return -1;
            }
            return this.g.indexOf(((Character) obj).charValue());
        }

        public final boolean isPartialView() {
            return false;
        }

        public int lastIndexOf(Object obj) {
            if (!(obj instanceof Character)) {
                return -1;
            }
            return this.g.lastIndexOf(((Character) obj).charValue());
        }

        public int size() {
            return this.g.length();
        }

        public Character get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Character.valueOf(this.g.charAt(i));
        }

        public ImmutableList<Character> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            return Lists.charactersOf(this.g.substring(i, i2));
        }
    }

    public static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 0;
        public final List<F> c;
        public final Function<? super F, ? extends T> f;

        public TransformingRandomAccessList(List<F> list, Function<? super F, ? extends T> function) {
            this.c = (List) Preconditions.checkNotNull(list);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.c.clear();
        }

        public T get(int i) {
            return this.f.apply(this.c.get(i));
        }

        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        public java.util.Iterator<T> iterator() {
            return listIterator();
        }

        public ListIterator<T> listIterator(int i) {
            return new TransformedListIterator<F, T>(this.c.listIterator(i)) {
                public final T a(F f2) {
                    return TransformingRandomAccessList.this.f.apply(f2);
                }
            };
        }

        public T remove(int i) {
            return this.f.apply(this.c.remove(i));
        }

        public int size() {
            return this.c.size();
        }
    }

    public static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
        private static final long serialVersionUID = 0;
        public final List<F> c;
        public final Function<? super F, ? extends T> f;

        public TransformingSequentialList(List<F> list, Function<? super F, ? extends T> function) {
            this.c = (List) Preconditions.checkNotNull(list);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.c.clear();
        }

        public ListIterator<T> listIterator(int i) {
            return new TransformedListIterator<F, T>(this.c.listIterator(i)) {
                public final T a(F f2) {
                    return TransformingSequentialList.this.f.apply(f2);
                }
            };
        }

        public int size() {
            return this.c.size();
        }
    }

    public static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        public final E c;
        public final E f;
        public final E[] g;

        public TwoPlusArrayList(E e, E e2, E[] eArr) {
            this.c = e;
            this.f = e2;
            this.g = (Object[]) Preconditions.checkNotNull(eArr);
        }

        public E get(int i) {
            if (i == 0) {
                return this.c;
            }
            if (i == 1) {
                return this.f;
            }
            Preconditions.checkElementIndex(i, size());
            return this.g[i - 2];
        }

        public int size() {
            return y3.b(((long) this.g.length) + ((long) 2));
        }
    }

    public static <E> List<E> asList(E e, E[] eArr) {
        return new OnePlusArrayList(e, eArr);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        int i = CartesianList.g;
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        for (List copyOf : list) {
            ImmutableList copyOf2 = ImmutableList.copyOf(copyOf);
            if (copyOf2.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add((Object) copyOf2);
        }
        return new CartesianList(builder.build());
    }

    public static ImmutableList<Character> charactersOf(String str) {
        return new StringAsImmutableList((String) Preconditions.checkNotNull(str));
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int i) {
        CollectPreconditions.b(i, "initialArraySize");
        return new ArrayList<>(i);
    }

    public static <E> ArrayList<E> newArrayListWithExpectedSize(int i) {
        CollectPreconditions.b(i, "arraySize");
        return new ArrayList<>(y3.b(((long) i) + 5 + ((long) (i / 10))));
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    public static <T> List<List<T>> partition(List<T> list, int i) {
        boolean z;
        Preconditions.checkNotNull(list);
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (list instanceof RandomAccess) {
            return new RandomAccessPartition(list, i);
        }
        return new Partition(list, i);
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList) list).reverse();
        }
        if (list instanceof ReverseList) {
            return ((ReverseList) list).c;
        }
        if (list instanceof RandomAccess) {
            return new RandomAccessReverseList(list);
        }
        return new ReverseList(list);
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list instanceof RandomAccess ? new TransformingRandomAccessList(list, function) : new TransformingSequentialList(list, function);
    }

    public static <E> List<E> asList(E e, E e2, E[] eArr) {
        return new TwoPlusArrayList(e, e2, eArr);
    }

    public static List<Character> charactersOf(CharSequence charSequence) {
        return new CharSequenceAsList((CharSequence) Preconditions.checkNotNull(charSequence));
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        Preconditions.checkNotNull(eArr);
        int length = eArr.length;
        CollectPreconditions.b(length, "arraySize");
        ArrayList<E> arrayList = new ArrayList<>(y3.b(((long) length) + 5 + ((long) (length / 10))));
        Collections.addAll(arrayList, eArr);
        return arrayList;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Iterable<? extends E>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <E> java.util.concurrent.CopyOnWriteArrayList<E> newCopyOnWriteArrayList(java.lang.Iterable<? extends E> r1) {
        /*
            boolean r0 = r1 instanceof java.util.Collection
            if (r0 == 0) goto L_0x0007
            java.util.Collection r1 = (java.util.Collection) r1
            goto L_0x000b
        L_0x0007:
            java.util.ArrayList r1 = newArrayList(r1)
        L_0x000b:
            java.util.concurrent.CopyOnWriteArrayList r0 = new java.util.concurrent.CopyOnWriteArrayList
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Lists.newCopyOnWriteArrayList(java.lang.Iterable):java.util.concurrent.CopyOnWriteArrayList");
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> newLinkedList = newLinkedList();
        Iterables.addAll(newLinkedList, iterable);
        return newLinkedList;
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            return new ArrayList<>((Collection) iterable);
        }
        return newArrayList(iterable.iterator());
    }

    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B>... listArr) {
        return cartesianProduct(Arrays.asList(listArr));
    }

    public static <E> ArrayList<E> newArrayList(java.util.Iterator<? extends E> it) {
        ArrayList<E> newArrayList = newArrayList();
        Iterators.addAll(newArrayList, it);
        return newArrayList;
    }
}
