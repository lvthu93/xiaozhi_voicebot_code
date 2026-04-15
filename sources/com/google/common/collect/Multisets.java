package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import j$.util.Iterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public final class Multisets {

    public static abstract class AbstractEntry<E> implements Multiset.Entry<E> {
        public boolean equals(Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            if (getCount() != entry.getCount() || !Objects.equal(getElement(), entry.getElement())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i;
            Object element = getElement();
            if (element == null) {
                i = 0;
            } else {
                i = element.hashCode();
            }
            return i ^ getCount();
        }

        public String toString() {
            String valueOf = String.valueOf(getElement());
            int count = getCount();
            if (count == 1) {
                return valueOf;
            }
            return valueOf + " x " + count;
        }
    }

    public static final class DecreasingCount implements Comparator<Multiset.Entry<?>> {
        public static final DecreasingCount c = new DecreasingCount();

        public int compare(Multiset.Entry<?> entry, Multiset.Entry<?> entry2) {
            return entry2.getCount() - entry.getCount();
        }
    }

    public static abstract class ElementSet<E> extends Sets.ImprovedAbstractSet<E> {
        public abstract Multiset<E> a();

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            return a().contains(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            return a().containsAll(collection);
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public abstract Iterator<E> iterator();

        public boolean remove(Object obj) {
            return a().remove(obj, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) > 0;
        }

        public int size() {
            return a().entrySet().size();
        }
    }

    public static abstract class EntrySet<E> extends Sets.ImprovedAbstractSet<Multiset.Entry<E>> {
        public abstract Multiset<E> a();

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry entry = (Multiset.Entry) obj;
            if (entry.getCount() > 0 && a().count(entry.getElement()) == entry.getCount()) {
                return true;
            }
            return false;
        }

        public boolean remove(Object obj) {
            if (obj instanceof Multiset.Entry) {
                Multiset.Entry entry = (Multiset.Entry) obj;
                Object element = entry.getElement();
                int count = entry.getCount();
                if (count != 0) {
                    return a().setCount(element, count, 0);
                }
            }
            return false;
        }
    }

    public static final class FilteredMultiset<E> extends ViewMultiset<E> {
        public final Multiset<E> g;
        public final Predicate<? super E> h;

        public FilteredMultiset(Multiset<E> multiset, Predicate<? super E> predicate) {
            this.g = (Multiset) Preconditions.checkNotNull(multiset);
            this.h = (Predicate) Preconditions.checkNotNull(predicate);
        }

        public final Set<E> a() {
            return Sets.filter(this.g.elementSet(), this.h);
        }

        public int add(E e, int i) {
            Predicate<? super E> predicate = this.h;
            Preconditions.checkArgument(predicate.apply(e), "Element %s does not match predicate %s", (Object) e, (Object) predicate);
            return this.g.add(e, i);
        }

        public final Iterator<E> c() {
            throw new AssertionError("should never be called");
        }

        public int count(Object obj) {
            int count = this.g.count(obj);
            if (count <= 0 || !this.h.apply(obj)) {
                return 0;
            }
            return count;
        }

        public final Set<Multiset.Entry<E>> createEntrySet() {
            return Sets.filter(this.g.entrySet(), new Predicate<Multiset.Entry<E>>() {
                public boolean apply(Multiset.Entry<E> entry) {
                    return FilteredMultiset.this.h.apply(entry.getElement());
                }
            });
        }

        public final Iterator<Multiset.Entry<E>> f() {
            throw new AssertionError("should never be called");
        }

        public int remove(Object obj, int i) {
            CollectPreconditions.b(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            if (contains(obj)) {
                return this.g.remove(obj, i);
            }
            return 0;
        }

        public UnmodifiableIterator<E> iterator() {
            return Iterators.filter(this.g.iterator(), this.h);
        }
    }

    public static class ImmutableEntry<E> extends AbstractEntry<E> implements Serializable {
        private static final long serialVersionUID = 0;
        public final E c;
        public final int f;

        public ImmutableEntry(int i, Object obj) {
            this.c = obj;
            this.f = i;
            CollectPreconditions.b(i, "count");
        }

        public final int getCount() {
            return this.f;
        }

        public final E getElement() {
            return this.c;
        }

        public ImmutableEntry<E> nextInBucket() {
            return null;
        }
    }

    public static final class MultisetIteratorImpl<E> implements Iterator<E>, j$.util.Iterator {
        public final Multiset<E> c;
        public final Iterator<Multiset.Entry<E>> f;
        public Multiset.Entry<E> g;
        public int h;
        public int i;
        public boolean j;

        public MultisetIteratorImpl(Multiset<E> multiset, Iterator<Multiset.Entry<E>> it) {
            this.c = multiset;
            this.f = it;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.h > 0 || this.f.hasNext();
        }

        public E next() {
            if (hasNext()) {
                if (this.h == 0) {
                    Multiset.Entry<E> next = this.f.next();
                    this.g = next;
                    int count = next.getCount();
                    this.h = count;
                    this.i = count;
                }
                this.h--;
                this.j = true;
                return this.g.getElement();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.e(this.j);
            if (this.i == 1) {
                this.f.remove();
            } else {
                this.c.remove(this.g.getElement());
            }
            this.i--;
            this.j = false;
        }
    }

    public static class UnmodifiableMultiset<E> extends ForwardingMultiset<E> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Multiset<? extends E> c;
        public transient Set<E> f;
        public transient Set<Multiset.Entry<E>> g;

        public UnmodifiableMultiset(Multiset<? extends E> multiset) {
            this.c = multiset;
        }

        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Set<E> elementSet() {
            Set<E> set = this.f;
            if (set != null) {
                return set;
            }
            Set<E> j = j();
            this.f = j;
            return j;
        }

        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set = this.g;
            if (set != null) {
                return set;
            }
            Set<Multiset.Entry<E>> unmodifiableSet = Collections.unmodifiableSet(this.c.entrySet());
            this.g = unmodifiableSet;
            return unmodifiableSet;
        }

        /* renamed from: g */
        public Multiset<E> delegate() {
            return this.c;
        }

        public java.util.Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.c.iterator());
        }

        public Set<E> j() {
            return Collections.unmodifiableSet(this.c.elementSet());
        }

        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        public int setCount(E e, int i) {
            throw new UnsupportedOperationException();
        }

        public int add(E e, int i) {
            throw new UnsupportedOperationException();
        }

        public int remove(Object obj, int i) {
            throw new UnsupportedOperationException();
        }

        public boolean setCount(E e, int i, int i2) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class ViewMultiset<E> extends AbstractMultiset<E> {
        public int b() {
            return elementSet().size();
        }

        public void clear() {
            elementSet().clear();
        }

        public java.util.Iterator<E> iterator() {
            return Multisets.c(this);
        }

        public int size() {
            long j = 0;
            for (Multiset.Entry count : entrySet()) {
                j += (long) count.getCount();
            }
            return y3.b(j);
        }
    }

    public static boolean a(Multiset<?> multiset, Object obj) {
        if (obj == multiset) {
            return true;
        }
        if (obj instanceof Multiset) {
            Multiset multiset2 = (Multiset) obj;
            if (multiset.size() == multiset2.size() && multiset.entrySet().size() == multiset2.entrySet().size()) {
                for (Multiset.Entry entry : multiset2.entrySet()) {
                    if (multiset.count(entry.getElement()) != entry.getCount()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static int b(Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return ((Multiset) iterable).elementSet().size();
        }
        return 11;
    }

    public static <E> java.util.Iterator<E> c(Multiset<E> multiset) {
        return new MultisetIteratorImpl(multiset, multiset.entrySet().iterator());
    }

    public static boolean containsOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        for (Multiset.Entry next : multiset2.entrySet()) {
            if (multiset.count(next.getElement()) < next.getCount()) {
                return false;
            }
        }
        return true;
    }

    public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> multiset) {
        Multiset.Entry[] entryArr = (Multiset.Entry[]) multiset.entrySet().toArray(new Multiset.Entry[0]);
        Arrays.sort(entryArr, DecreasingCount.c);
        List<Multiset.Entry> asList = Arrays.asList(entryArr);
        int i = ImmutableMultiset.h;
        ImmutableMultiset.Builder builder = new ImmutableMultiset.Builder(asList.size());
        for (Multiset.Entry entry : asList) {
            builder.addCopies(entry.getElement(), entry.getCount());
        }
        return builder.build();
    }

    public static <E> Multiset<E> difference(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() {
            public final int b() {
                return Iterators.size(f());
            }

            public final java.util.Iterator<E> c() {
                final java.util.Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<E>() {
                    public final E computeNext() {
                        Multiset.Entry entry;
                        E element;
                        do {
                            java.util.Iterator it = it;
                            if (it.hasNext()) {
                                entry = (Multiset.Entry) it.next();
                                element = entry.getElement();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (entry.getCount() <= multiset2.count(element));
                        return element;
                    }
                };
            }

            public void clear() {
                throw new UnsupportedOperationException();
            }

            public int count(Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.max(0, count - multiset2.count(obj));
            }

            public final java.util.Iterator<Multiset.Entry<E>> f() {
                final java.util.Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() {
                    public final Object computeNext() {
                        Object element;
                        int count;
                        do {
                            java.util.Iterator it = it;
                            if (it.hasNext()) {
                                Multiset.Entry entry = (Multiset.Entry) it.next();
                                element = entry.getElement();
                                count = entry.getCount() - multiset2.count(element);
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (count <= 0);
                        return Multisets.immutableEntry(element, count);
                    }
                };
            }
        };
    }

    public static <E> Multiset<E> filter(Multiset<E> multiset, Predicate<? super E> predicate) {
        if (!(multiset instanceof FilteredMultiset)) {
            return new FilteredMultiset(multiset, predicate);
        }
        FilteredMultiset filteredMultiset = (FilteredMultiset) multiset;
        return new FilteredMultiset(filteredMultiset.g, Predicates.and(filteredMultiset.h, predicate));
    }

    public static <E> Multiset.Entry<E> immutableEntry(E e, int i) {
        return new ImmutableEntry(i, e);
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() {
            public final Set<E> a() {
                return Sets.intersection(multiset.elementSet(), multiset2.elementSet());
            }

            public final java.util.Iterator<E> c() {
                throw new AssertionError("should never be called");
            }

            public int count(Object obj) {
                int count = multiset.count(obj);
                if (count == 0) {
                    return 0;
                }
                return Math.min(count, multiset2.count(obj));
            }

            public final java.util.Iterator<Multiset.Entry<E>> f() {
                final java.util.Iterator it = multiset.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() {
                    public final Object computeNext() {
                        Object element;
                        int min;
                        do {
                            java.util.Iterator it = it;
                            if (it.hasNext()) {
                                Multiset.Entry entry = (Multiset.Entry) it.next();
                                element = entry.getElement();
                                min = Math.min(entry.getCount(), multiset2.count(element));
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (min <= 0);
                        return Multisets.immutableEntry(element, min);
                    }
                };
            }
        };
    }

    public static boolean removeOccurrences(Multiset<?> multiset, Iterable<?> iterable) {
        if (iterable instanceof Multiset) {
            return removeOccurrences(multiset, (Multiset<?>) (Multiset) iterable);
        }
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(iterable);
        boolean z = false;
        for (Object remove : iterable) {
            z |= multiset.remove(remove);
        }
        return z;
    }

    public static boolean retainOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        java.util.Iterator<Multiset.Entry<?>> it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Multiset.Entry next = it.next();
            int count = multiset2.count(next.getElement());
            if (count == 0) {
                it.remove();
            } else if (count < next.getCount()) {
                multiset.setCount(next.getElement(), count);
            }
            z = true;
        }
        return z;
    }

    public static <E> Multiset<E> sum(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() {
            public final Set<E> a() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            public final java.util.Iterator<E> c() {
                throw new AssertionError("should never be called");
            }

            public boolean contains(Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            public int count(Object obj) {
                return multiset2.count(obj) + multiset.count(obj);
            }

            public final java.util.Iterator<Multiset.Entry<E>> f() {
                final java.util.Iterator it = multiset.entrySet().iterator();
                final java.util.Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() {
                    public final Object computeNext() {
                        Multiset.Entry entry;
                        Object element;
                        java.util.Iterator it = it;
                        boolean hasNext = it.hasNext();
                        AnonymousClass3 r2 = AnonymousClass3.this;
                        if (hasNext) {
                            Multiset.Entry entry2 = (Multiset.Entry) it.next();
                            Object element2 = entry2.getElement();
                            return Multisets.immutableEntry(element2, multiset2.count(element2) + entry2.getCount());
                        }
                        do {
                            java.util.Iterator it2 = it2;
                            if (it2.hasNext()) {
                                entry = (Multiset.Entry) it2.next();
                                element = entry.getElement();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (multiset.contains(element));
                        return Multisets.immutableEntry(element, entry.getCount());
                    }
                };
            }

            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }

            public int size() {
                return y3.b(((long) multiset.size()) + ((long) multiset2.size()));
            }
        };
    }

    public static <E> Multiset<E> union(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>() {
            public final Set<E> a() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            public final java.util.Iterator<E> c() {
                throw new AssertionError("should never be called");
            }

            public boolean contains(Object obj) {
                return multiset.contains(obj) || multiset2.contains(obj);
            }

            public int count(Object obj) {
                return Math.max(multiset.count(obj), multiset2.count(obj));
            }

            public final java.util.Iterator<Multiset.Entry<E>> f() {
                final java.util.Iterator it = multiset.entrySet().iterator();
                final java.util.Iterator it2 = multiset2.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>() {
                    public final Object computeNext() {
                        Multiset.Entry entry;
                        Object element;
                        java.util.Iterator it = it;
                        boolean hasNext = it.hasNext();
                        AnonymousClass1 r2 = AnonymousClass1.this;
                        if (hasNext) {
                            Multiset.Entry entry2 = (Multiset.Entry) it.next();
                            Object element2 = entry2.getElement();
                            return Multisets.immutableEntry(element2, Math.max(entry2.getCount(), multiset2.count(element2)));
                        }
                        do {
                            java.util.Iterator it2 = it2;
                            if (it2.hasNext()) {
                                entry = (Multiset.Entry) it2.next();
                                element = entry.getElement();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (multiset.contains(element));
                        return Multisets.immutableEntry(element, entry.getCount());
                    }
                };
            }

            public boolean isEmpty() {
                return multiset.isEmpty() && multiset2.isEmpty();
            }
        };
    }

    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        return ((multiset instanceof UnmodifiableMultiset) || (multiset instanceof ImmutableMultiset)) ? multiset : new UnmodifiableMultiset((Multiset) Preconditions.checkNotNull(multiset));
    }

    public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        return new UnmodifiableSortedMultiset((SortedMultiset) Preconditions.checkNotNull(sortedMultiset));
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> immutableMultiset) {
        return (Multiset) Preconditions.checkNotNull(immutableMultiset);
    }

    public static boolean removeOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        java.util.Iterator<Multiset.Entry<?>> it = multiset.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Multiset.Entry next = it.next();
            int count = multiset2.count(next.getElement());
            if (count >= next.getCount()) {
                it.remove();
            } else if (count > 0) {
                multiset.remove(next.getElement(), count);
            }
            z = true;
        }
        return z;
    }
}
