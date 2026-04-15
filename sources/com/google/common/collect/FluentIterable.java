package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

public abstract class FluentIterable<E> implements Iterable<E> {
    public final Optional<Iterable<E>> c;

    public static class FromIterableFunction<E> implements Function<Iterable<E>, FluentIterable<E>> {
        public FluentIterable<E> apply(Iterable<E> iterable) {
            return FluentIterable.from(iterable);
        }
    }

    public FluentIterable() {
        this.c = Optional.absent();
    }

    public static <T> FluentIterable<T> a(final Iterable<? extends T>... iterableArr) {
        for (Iterable<? extends T> checkNotNull : iterableArr) {
            Preconditions.checkNotNull(checkNotNull);
        }
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat(new AbstractIndexedListIterator<Iterator<? extends T>>(iterableArr.length) {
                    public Iterator<? extends T> get(int i) {
                        return iterableArr[i].iterator();
                    }
                });
            }
        };
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return a(iterable, iterable2);
    }

    public static <E> FluentIterable<E> from(final Iterable<E> iterable) {
        return iterable instanceof FluentIterable ? (FluentIterable) iterable : new FluentIterable<E>(iterable) {
            public Iterator<E> iterator() {
                return iterable.iterator();
            }
        };
    }

    public static <E> FluentIterable<E> of() {
        return from(ImmutableList.of());
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(b(), predicate);
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(b(), predicate);
    }

    public final FluentIterable<E> append(Iterable<? extends E> iterable) {
        return concat(b(), iterable);
    }

    public final Iterable<E> b() {
        return this.c.or(this);
    }

    public final boolean contains(Object obj) {
        return Iterables.contains(b(), obj);
    }

    public final <C extends Collection<? super E>> C copyInto(C c2) {
        Preconditions.checkNotNull(c2);
        Iterable<Object> b = b();
        if (b instanceof Collection) {
            c2.addAll((Collection) b);
        } else {
            for (Object add : b) {
                c2.add(add);
            }
        }
        return c2;
    }

    public final FluentIterable<E> cycle() {
        return from(Iterables.cycle(b()));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return from(Iterables.filter(b(), predicate));
    }

    public final Optional<E> first() {
        Iterator it = b().iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        }
        return Optional.absent();
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(b(), predicate);
    }

    public final E get(int i) {
        return Iterables.get(b(), i);
    }

    public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> function) {
        return Multimaps.index(b(), function);
    }

    public final boolean isEmpty() {
        return !b().iterator().hasNext();
    }

    public final String join(Joiner joiner) {
        return joiner.join((Iterable<?>) this);
    }

    public final Optional<E> last() {
        Object next;
        Iterable b = b();
        if (b instanceof List) {
            List list = (List) b;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.of(list.get(list.size() - 1));
        }
        Iterator it = b.iterator();
        if (!it.hasNext()) {
            return Optional.absent();
        }
        if (b instanceof SortedSet) {
            return Optional.of(((SortedSet) b).last());
        }
        do {
            next = it.next();
        } while (it.hasNext());
        return Optional.of(next);
    }

    public final FluentIterable<E> limit(int i) {
        return from(Iterables.limit(b(), i));
    }

    public final int size() {
        return Iterables.size(b());
    }

    public final FluentIterable<E> skip(int i) {
        return from(Iterables.skip(b(), i));
    }

    public final E[] toArray(Class<E> cls) {
        return Iterables.toArray(b(), cls);
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(b());
    }

    public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> function) {
        return Maps.toMap(b(), function);
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(b());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(b());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(b());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, b());
    }

    public String toString() {
        return Iterables.toString(b());
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return from(Iterables.transform(b(), function));
    }

    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return concat(transform(function));
    }

    public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> function) {
        return Maps.uniqueIndex(b(), function);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return a(iterable, iterable2, iterable3);
    }

    public static <E> FluentIterable<E> from(E[] eArr) {
        return from(Arrays.asList(eArr));
    }

    public static <E> FluentIterable<E> of(E e, E... eArr) {
        return from(Lists.asList(e, eArr));
    }

    public final FluentIterable<E> append(E... eArr) {
        return concat(b(), Arrays.asList(eArr));
    }

    public final <T> FluentIterable<T> filter(Class<T> cls) {
        return from(Iterables.filter((Iterable<?>) b(), cls));
    }

    public FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        this.c = Optional.fromNullable(this == iterable ? null : iterable);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return a(iterable, iterable2, iterable3, iterable4);
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> fluentIterable) {
        return (FluentIterable) Preconditions.checkNotNull(fluentIterable);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T>... iterableArr) {
        return a((Iterable[]) Arrays.copyOf(iterableArr, iterableArr.length));
    }

    public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat(Iterators.transform(iterable.iterator(), new Function<Iterable<Object>, Iterator<Object>>() {
                    public Iterator<Object> apply(Iterable<Object> iterable) {
                        return iterable.iterator();
                    }
                }));
            }
        };
    }
}
