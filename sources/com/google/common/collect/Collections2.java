package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class Collections2 {

    public static class FilteredCollection<E> extends AbstractCollection<E> {
        public final Collection<E> c;
        public final Predicate<? super E> f;

        public FilteredCollection(Collection<E> collection, Predicate<? super E> predicate) {
            this.c = collection;
            this.f = predicate;
        }

        public boolean add(E e) {
            Preconditions.checkArgument(this.f.apply(e));
            return this.c.add(e);
        }

        public boolean addAll(Collection<? extends E> collection) {
            for (Object apply : collection) {
                Preconditions.checkArgument(this.f.apply(apply));
            }
            return this.c.addAll(collection);
        }

        public void clear() {
            Iterables.removeIf(this.c, this.f);
        }

        public boolean contains(Object obj) {
            if (Collections2.d(obj, this.c)) {
                return this.f.apply(obj);
            }
            return false;
        }

        public boolean containsAll(Collection<?> collection) {
            return Collections2.b(this, collection);
        }

        public boolean isEmpty() {
            return !Iterables.any(this.c, this.f);
        }

        public Iterator<E> iterator() {
            return Iterators.filter(this.c.iterator(), this.f);
        }

        public boolean remove(Object obj) {
            return contains(obj) && this.c.remove(obj);
        }

        public boolean removeAll(Collection<?> collection) {
            Iterator<E> it = this.c.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.f.apply(next) && collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator<E> it = this.c.iterator();
            boolean z = false;
            while (it.hasNext()) {
                E next = it.next();
                if (this.f.apply(next) && !collection.contains(next)) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public int size() {
            int i = 0;
            for (E apply : this.c) {
                if (this.f.apply(apply)) {
                    i++;
                }
            }
            return i;
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    public static final class OrderedPermutationCollection<E> extends AbstractCollection<List<E>> {
        public final ImmutableList<E> c;
        public final Comparator<? super E> f;
        public final int g;

        public OrderedPermutationCollection(Iterable<E> iterable, Comparator<? super E> comparator) {
            int b;
            ImmutableList<E> sortedCopyOf = ImmutableList.sortedCopyOf(comparator, iterable);
            this.c = sortedCopyOf;
            this.f = comparator;
            int i = 1;
            int i2 = 1;
            int i3 = 1;
            while (true) {
                if (i >= sortedCopyOf.size()) {
                    b = y3.b(((long) i3) * ((long) w3.a(i, i2)));
                    break;
                }
                if (comparator.compare(sortedCopyOf.get(i - 1), sortedCopyOf.get(i)) < 0) {
                    i3 = y3.b(((long) i3) * ((long) w3.a(i, i2)));
                    b = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                    if (i3 == Integer.MAX_VALUE) {
                        break;
                    }
                    i2 = 0;
                }
                i++;
                i2++;
            }
            this.g = b;
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.a(this.c, (List) obj);
        }

        public boolean isEmpty() {
            return false;
        }

        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator(this.c, this.f);
        }

        public int size() {
            return this.g;
        }

        public String toString() {
            return "orderedPermutationCollection(" + this.c + ")";
        }
    }

    public static final class OrderedPermutationIterator<E> extends AbstractIterator<List<E>> {
        public ArrayList g;
        public final Comparator<? super E> h;

        public OrderedPermutationIterator(ImmutableList immutableList, Comparator comparator) {
            this.g = Lists.newArrayList(immutableList);
            this.h = comparator;
        }

        public final Object computeNext() {
            Comparator<? super E> comparator;
            ArrayList arrayList = this.g;
            if (arrayList == null) {
                this.c = AbstractIterator.State.DONE;
                return null;
            }
            ImmutableList copyOf = ImmutableList.copyOf(arrayList);
            int size = this.g.size() - 2;
            while (true) {
                comparator = this.h;
                if (size < 0) {
                    size = -1;
                    break;
                } else if (comparator.compare(this.g.get(size), this.g.get(size + 1)) < 0) {
                    break;
                } else {
                    size--;
                }
            }
            if (size == -1) {
                this.g = null;
            } else {
                Object obj = this.g.get(size);
                int size2 = this.g.size() - 1;
                while (size2 > size) {
                    if (comparator.compare(obj, this.g.get(size2)) < 0) {
                        Collections.swap(this.g, size, size2);
                        Collections.reverse(this.g.subList(size + 1, this.g.size()));
                    } else {
                        size2--;
                    }
                }
                throw new AssertionError("this statement should be unreachable");
            }
            return copyOf;
        }
    }

    public static final class PermutationCollection<E> extends AbstractCollection<List<E>> {
        public final ImmutableList<E> c;

        public PermutationCollection(ImmutableList<E> immutableList) {
            this.c = immutableList;
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            return Collections2.a(this.c, (List) obj);
        }

        public boolean isEmpty() {
            return false;
        }

        public Iterator<List<E>> iterator() {
            return new PermutationIterator(this.c);
        }

        public int size() {
            int size = this.c.size();
            cg.e(size, "n");
            if (size < 13) {
                return w3.a[size];
            }
            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }

        public String toString() {
            return "permutations(" + this.c + ")";
        }
    }

    public static class PermutationIterator<E> extends AbstractIterator<List<E>> {
        public final ArrayList g;
        public final int[] h;
        public final int[] i;
        public int j = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

        public PermutationIterator(ImmutableList immutableList) {
            this.g = new ArrayList(immutableList);
            int size = immutableList.size();
            int[] iArr = new int[size];
            this.h = iArr;
            int[] iArr2 = new int[size];
            this.i = iArr2;
            Arrays.fill(iArr, 0);
            Arrays.fill(iArr2, 1);
        }

        public final Object computeNext() {
            if (this.j <= 0) {
                this.c = AbstractIterator.State.DONE;
                return null;
            }
            ArrayList arrayList = this.g;
            ImmutableList copyOf = ImmutableList.copyOf(arrayList);
            int size = arrayList.size() - 1;
            this.j = size;
            if (size != -1) {
                int i2 = 0;
                while (true) {
                    int i3 = this.j;
                    int[] iArr = this.h;
                    int i4 = iArr[i3];
                    int[] iArr2 = this.i;
                    int i5 = iArr2[i3];
                    int i6 = i5 + i4;
                    if (i6 >= 0) {
                        if (i6 != i3 + 1) {
                            Collections.swap(arrayList, (i3 - i4) + i2, (i3 - i6) + i2);
                            iArr[this.j] = i6;
                            break;
                        } else if (i3 == 0) {
                            break;
                        } else {
                            i2++;
                            iArr2[i3] = -i5;
                            this.j = i3 - 1;
                        }
                    } else {
                        iArr2[i3] = -i5;
                        this.j = i3 - 1;
                    }
                }
            }
            return copyOf;
        }
    }

    public static class TransformedCollection<F, T> extends AbstractCollection<T> {
        public final Collection<F> c;
        public final Function<? super F, ? extends T> f;

        public TransformedCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.c = (Collection) Preconditions.checkNotNull(collection);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        public void clear() {
            this.c.clear();
        }

        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        public Iterator<T> iterator() {
            return Iterators.transform(this.c.iterator(), this.f);
        }

        public int size() {
            return this.c.size();
        }
    }

    public static boolean a(ImmutableList immutableList, List list) {
        if (immutableList.size() != list.size()) {
            return false;
        }
        ObjectCountHashMap c = c(immutableList);
        ObjectCountHashMap c2 = c(list);
        if (immutableList.size() != list.size()) {
            return false;
        }
        for (int i = 0; i < immutableList.size(); i++) {
            if (c.d(i) != c2.get(c.c(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean b(Collection<?> collection, Collection<?> collection2) {
        for (Object contains : collection2) {
            if (!collection.contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public static <E> ObjectCountHashMap<E> c(Collection<E> collection) {
        ObjectCountHashMap<E> objectCountHashMap = new ObjectCountHashMap<>();
        for (E next : collection) {
            objectCountHashMap.put(next, objectCountHashMap.get(next) + 1);
        }
        return objectCountHashMap;
    }

    public static boolean d(Object obj, Collection collection) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        if (!(collection instanceof FilteredCollection)) {
            return new FilteredCollection((Collection) Preconditions.checkNotNull(collection), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredCollection filteredCollection = (FilteredCollection) collection;
        return new FilteredCollection(filteredCollection.c, Predicates.and(filteredCollection.f, predicate));
    }

    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return orderedPermutations(iterable, Ordering.natural());
    }

    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection(ImmutableList.copyOf(collection));
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection(collection, function);
    }

    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection(iterable, comparator);
    }
}
