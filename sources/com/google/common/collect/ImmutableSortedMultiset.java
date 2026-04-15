package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public abstract class ImmutableSortedMultiset<E> extends ImmutableSortedMultisetFauxverideShim<E> implements SortedMultiset<E> {
    public transient ImmutableSortedMultiset<E> i;

    public static class Builder<E> extends ImmutableMultiset.Builder<E> {
        public final Comparator<? super E> d;
        public E[] e = new Object[4];
        public int[] f = new int[4];
        public int g;
        public boolean h;

        public Builder(Comparator<? super E> comparator) {
            super((Object) null);
            this.d = (Comparator) Preconditions.checkNotNull(comparator);
        }

        public final void b(boolean z) {
            int i = this.g;
            if (i != 0) {
                E[] copyOf = Arrays.copyOf(this.e, i);
                Comparator<? super E> comparator = this.d;
                Arrays.sort(copyOf, comparator);
                int i2 = 1;
                for (int i3 = 1; i3 < copyOf.length; i3++) {
                    if (comparator.compare(copyOf[i2 - 1], copyOf[i3]) < 0) {
                        copyOf[i2] = copyOf[i3];
                        i2++;
                    }
                }
                Arrays.fill(copyOf, i2, this.g, (Object) null);
                if (z) {
                    int i4 = i2 * 4;
                    int i5 = this.g;
                    if (i4 > i5 * 3) {
                        copyOf = Arrays.copyOf(copyOf, y3.b(((long) i5) + ((long) ((i5 / 2) + 1))));
                    }
                }
                int[] iArr = new int[copyOf.length];
                for (int i6 = 0; i6 < this.g; i6++) {
                    int binarySearch = Arrays.binarySearch(copyOf, 0, i2, this.e[i6], comparator);
                    int i7 = this.f[i6];
                    if (i7 >= 0) {
                        iArr[binarySearch] = iArr[binarySearch] + i7;
                    } else {
                        iArr[binarySearch] = ~i7;
                    }
                }
                this.e = copyOf;
                this.f = iArr;
                this.g = i2;
            }
        }

        public Builder<E> addCopies(E e2, int i) {
            Preconditions.checkNotNull(e2);
            CollectPreconditions.b(i, "occurrences");
            if (i == 0) {
                return this;
            }
            int i2 = this.g;
            E[] eArr = this.e;
            if (i2 == eArr.length) {
                b(true);
            } else if (this.h) {
                this.e = Arrays.copyOf(eArr, eArr.length);
            }
            this.h = false;
            E[] eArr2 = this.e;
            int i3 = this.g;
            eArr2[i3] = e2;
            this.f[i3] = i;
            this.g = i3 + 1;
            return this;
        }

        public Builder<E> setCount(E e2, int i) {
            Preconditions.checkNotNull(e2);
            CollectPreconditions.b(i, "count");
            int i2 = this.g;
            E[] eArr = this.e;
            if (i2 == eArr.length) {
                b(true);
            } else if (this.h) {
                this.e = Arrays.copyOf(eArr, eArr.length);
            }
            this.h = false;
            E[] eArr2 = this.e;
            int i3 = this.g;
            eArr2[i3] = e2;
            this.f[i3] = ~i;
            this.g = i3 + 1;
            return this;
        }

        public ImmutableSortedMultiset<E> build() {
            int i;
            b(false);
            int i2 = 0;
            int i3 = 0;
            while (true) {
                i = this.g;
                if (i2 >= i) {
                    break;
                }
                int[] iArr = this.f;
                int i4 = iArr[i2];
                if (i4 > 0) {
                    E[] eArr = this.e;
                    eArr[i3] = eArr[i2];
                    iArr[i3] = i4;
                    i3++;
                }
                i2++;
            }
            Arrays.fill(this.e, i3, i, (Object) null);
            Arrays.fill(this.f, i3, this.g, 0);
            this.g = i3;
            Comparator<? super E> comparator = this.d;
            if (i3 == 0) {
                return ImmutableSortedMultiset.l(comparator);
            }
            RegularImmutableSortedSet regularImmutableSortedSet = (RegularImmutableSortedSet) ImmutableSortedSet.m(comparator, i3, this.e);
            long[] jArr = new long[(this.g + 1)];
            int i5 = 0;
            while (i5 < this.g) {
                int i6 = i5 + 1;
                jArr[i6] = jArr[i5] + ((long) this.f[i5]);
                i5 = i6;
            }
            this.h = true;
            return new RegularImmutableSortedMultiset(regularImmutableSortedSet, jArr, 0, this.g);
        }

        public Builder<E> add(E e2) {
            return addCopies((Object) e2, 1);
        }

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Multiset) {
                for (Multiset.Entry entry : ((Multiset) iterable).entrySet()) {
                    addCopies(entry.getElement(), entry.getCount());
                }
            } else {
                for (Object add : iterable) {
                    add((Object) add);
                }
            }
            return this;
        }

        public Builder<E> add(E... eArr) {
            for (E add : eArr) {
                add((Object) add);
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            while (it.hasNext()) {
                add((Object) it.next());
            }
            return this;
        }
    }

    public static final class SerializedForm<E> implements Serializable {
        public final Comparator<? super E> c;
        public final E[] f;
        public final int[] g;

        public SerializedForm(SortedMultiset<E> sortedMultiset) {
            this.c = sortedMultiset.comparator();
            int size = sortedMultiset.entrySet().size();
            this.f = new Object[size];
            this.g = new int[size];
            int i = 0;
            for (Multiset.Entry next : sortedMultiset.entrySet()) {
                this.f[i] = next.getElement();
                this.g[i] = next.getCount();
                i++;
            }
        }

        public Object readResolve() {
            E[] eArr = this.f;
            int length = eArr.length;
            Builder builder = new Builder(this.c);
            for (int i = 0; i < length; i++) {
                builder.addCopies((Object) eArr[i], this.g[i]);
            }
            return builder.build();
        }
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(E[] eArr) {
        return copyOf(Ordering.natural(), Arrays.asList(eArr));
    }

    public static <E> ImmutableSortedMultiset<E> copyOfSorted(SortedMultiset<E> sortedMultiset) {
        return k(sortedMultiset.comparator(), Lists.newArrayList(sortedMultiset.entrySet()));
    }

    public static ImmutableSortedMultiset k(Comparator comparator, AbstractCollection abstractCollection) {
        if (abstractCollection.isEmpty()) {
            return l(comparator);
        }
        ImmutableList.Builder builder = new ImmutableList.Builder(abstractCollection.size());
        long[] jArr = new long[(abstractCollection.size() + 1)];
        Iterator it = abstractCollection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry) it.next();
            builder.add(entry.getElement());
            int i3 = i2 + 1;
            jArr[i3] = jArr[i2] + ((long) entry.getCount());
            i2 = i3;
        }
        return new RegularImmutableSortedMultiset(new RegularImmutableSortedSet(builder.build(), comparator), jArr, 0, abstractCollection.size());
    }

    public static <E> ImmutableSortedMultiset<E> l(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return RegularImmutableSortedMultiset.o;
        }
        return new RegularImmutableSortedMultiset(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <E> ImmutableSortedMultiset<E> of() {
        return RegularImmutableSortedMultiset.o;
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public final Comparator<? super E> comparator() {
        return elementSet().comparator();
    }

    public abstract ImmutableSortedSet<E> elementSet();

    public abstract ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType);

    @Deprecated
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public abstract ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType);

    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterable<? extends E> iterable) {
        return copyOf(Ordering.natural(), iterable);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e) {
        return new RegularImmutableSortedMultiset((RegularImmutableSortedSet) ImmutableSortedSet.of(e), new long[]{0, 1}, 0, 1);
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        ImmutableSortedMultiset<E> immutableSortedMultiset = this.i;
        if (immutableSortedMultiset == null) {
            immutableSortedMultiset = isEmpty() ? l(Ordering.from(comparator()).reverse()) : new DescendingImmutableSortedMultiset<>(this);
            this.i = immutableSortedMultiset;
        }
        return immutableSortedMultiset;
    }

    public ImmutableSortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        Preconditions.checkArgument(comparator().compare(e, e2) <= 0, "Expected lowerBound <= upperBound but %s > %s", (Object) e, (Object) e2);
        return tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Iterator<? extends E> it) {
        return copyOf(Ordering.natural(), it);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e, e2}));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> it) {
        Preconditions.checkNotNull(comparator);
        return new Builder(comparator).addAll((Iterator) it).build();
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e, e2, e3}));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e, e2, e3, e4}));
    }

    public static <E> ImmutableSortedMultiset<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        if (iterable instanceof ImmutableSortedMultiset) {
            ImmutableSortedMultiset<E> immutableSortedMultiset = (ImmutableSortedMultiset) iterable;
            if (comparator.equals(immutableSortedMultiset.comparator())) {
                return immutableSortedMultiset.isPartialView() ? k(comparator, immutableSortedMultiset.entrySet().asList()) : immutableSortedMultiset;
            }
        }
        return new Builder(comparator).addAll((Iterable) iterable).build();
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return copyOf(Ordering.natural(), Arrays.asList(new Comparable[]{e, e2, e3, e4, e5}));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(eArr.length + 6);
        Collections.addAll(newArrayListWithCapacity, new Comparable[]{e, e2, e3, e4, e5, e6});
        Collections.addAll(newArrayListWithCapacity, eArr);
        return copyOf(Ordering.natural(), newArrayListWithCapacity);
    }
}
