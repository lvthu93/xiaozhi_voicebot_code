package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import j$.util.Iterator;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public final class Iterators {

    public static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {
        public static final UnmodifiableListIterator<Object> i = new ArrayItr(new Object[0], 0);
        public final T[] g;
        public final int h = 0;

        public ArrayItr(Object[] objArr, int i2) {
            super(i2, 0);
            this.g = objArr;
        }

        public final T get(int i2) {
            return this.g[this.h + i2];
        }
    }

    public static class ConcatenatedIterator<T> implements Iterator<T>, j$.util.Iterator {
        public Iterator<? extends T> c;
        public Iterator<? extends T> f = ArrayItr.i;
        public Iterator<? extends Iterator<? extends T>> g;
        public ArrayDeque h;

        public ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> it) {
            this.g = (Iterator) Preconditions.checkNotNull(it);
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            java.util.Iterator<? extends java.util.Iterator<? extends T>> it;
            while (!((java.util.Iterator) Preconditions.checkNotNull(this.f)).hasNext()) {
                while (true) {
                    java.util.Iterator<? extends java.util.Iterator<? extends T>> it2 = this.g;
                    if (it2 != null && it2.hasNext()) {
                        it = this.g;
                        break;
                    }
                    ArrayDeque arrayDeque = this.h;
                    if (arrayDeque == null || arrayDeque.isEmpty()) {
                        it = null;
                    } else {
                        this.g = (java.util.Iterator) this.h.removeFirst();
                    }
                }
                it = null;
                this.g = it;
                if (it == null) {
                    return false;
                }
                java.util.Iterator<? extends T> it3 = (java.util.Iterator) it.next();
                this.f = it3;
                if (it3 instanceof ConcatenatedIterator) {
                    ConcatenatedIterator concatenatedIterator = (ConcatenatedIterator) it3;
                    this.f = concatenatedIterator.f;
                    if (this.h == null) {
                        this.h = new ArrayDeque();
                    }
                    this.h.addFirst(this.g);
                    if (concatenatedIterator.h != null) {
                        while (!concatenatedIterator.h.isEmpty()) {
                            this.h.addFirst(concatenatedIterator.h.removeLast());
                        }
                    }
                    this.g = concatenatedIterator.g;
                }
            }
            return true;
        }

        public T next() {
            if (hasNext()) {
                java.util.Iterator<? extends T> it = this.f;
                this.c = it;
                return it.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            boolean z;
            if (this.c != null) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.e(z);
            this.c.remove();
            this.c = null;
        }
    }

    public enum EmptyModifiableIterator implements java.util.Iterator<Object>, j$.util.Iterator {
        ;

        /* access modifiers changed from: public */
        EmptyModifiableIterator() {
        }

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.e(false);
        }
    }

    public static class MergingIterator<T> extends UnmodifiableIterator<T> {
        public final PriorityQueue c;

        public MergingIterator(Iterable<? extends java.util.Iterator<? extends T>> iterable, final Comparator<? super T> comparator) {
            this.c = new PriorityQueue(2, new Comparator<PeekingIterator<T>>() {
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return comparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
            });
            for (java.util.Iterator it : iterable) {
                if (it.hasNext()) {
                    this.c.add(Iterators.peekingIterator(it));
                }
            }
        }

        public boolean hasNext() {
            return !this.c.isEmpty();
        }

        public T next() {
            PriorityQueue priorityQueue = this.c;
            PeekingIterator peekingIterator = (PeekingIterator) priorityQueue.remove();
            T next = peekingIterator.next();
            if (peekingIterator.hasNext()) {
                priorityQueue.add(peekingIterator);
            }
            return next;
        }
    }

    public static class PeekingImpl<E> implements PeekingIterator<E>, j$.util.Iterator {
        public final java.util.Iterator<? extends E> c;
        public boolean f;
        public E g;

        public PeekingImpl(java.util.Iterator<? extends E> it) {
            this.c = (java.util.Iterator) Preconditions.checkNotNull(it);
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.f || this.c.hasNext();
        }

        public E next() {
            if (!this.f) {
                return this.c.next();
            }
            E e = this.g;
            this.f = false;
            this.g = null;
            return e;
        }

        public E peek() {
            if (!this.f) {
                this.g = this.c.next();
                this.f = true;
            }
            return this.g;
        }

        public void remove() {
            Preconditions.checkState(!this.f, "Can't remove after you've peeked at next");
            this.c.remove();
        }
    }

    public static void a(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("position (" + i + ") must not be negative");
        }
    }

    public static <T> boolean addAll(Collection<T> collection, java.util.Iterator<? extends T> it) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(it);
        boolean z = false;
        while (it.hasNext()) {
            z |= collection.add(it.next());
        }
        return z;
    }

    public static int advance(java.util.Iterator<?> it, int i) {
        boolean z;
        Preconditions.checkNotNull(it);
        int i2 = 0;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "numberToAdvance must be nonnegative");
        while (i2 < i && it.hasNext()) {
            it.next();
            i2++;
        }
        return i2;
    }

    public static <T> boolean all(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            if (!predicate.apply(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean any(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        return indexOf(it, predicate) != -1;
    }

    public static <T> Enumeration<T> asEnumeration(final java.util.Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new Enumeration<T>() {
            public boolean hasMoreElements() {
                return it.hasNext();
            }

            public T nextElement() {
                return it.next();
            }
        };
    }

    public static void b(java.util.Iterator<?> it) {
        Preconditions.checkNotNull(it);
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    public static <T> T c(java.util.Iterator<T> it) {
        if (!it.hasNext()) {
            return null;
        }
        T next = it.next();
        it.remove();
        return next;
    }

    public static <T> java.util.Iterator<T> concat(java.util.Iterator<? extends T> it, java.util.Iterator<? extends T> it2) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        final java.util.Iterator[] itArr = {it, it2};
        return concat(new UnmodifiableIterator<Object>() {
            public int c = 0;

            public boolean hasNext() {
                return this.c < r4.length;
            }

            public Object next() {
                if (hasNext()) {
                    int i = this.c;
                    Object[] objArr = r4;
                    Object obj = objArr[i];
                    objArr[i] = null;
                    this.c = i + 1;
                    return obj;
                }
                throw new NoSuchElementException();
            }
        });
    }

    public static <T> java.util.Iterator<T> consumingIterator(final java.util.Iterator<T> it) {
        Preconditions.checkNotNull(it);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                java.util.Iterator it = it;
                T next = it.next();
                it.remove();
                return next;
            }

            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }

    public static boolean contains(java.util.Iterator<?> it, Object obj) {
        if (obj == null) {
            while (it.hasNext()) {
                if (it.next() == null) {
                    return true;
                }
            }
            return false;
        }
        while (it.hasNext()) {
            if (obj.equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> java.util.Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Object() {
            public java.util.Iterator<T> c = EmptyModifiableIterator.c;

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                return this.c.hasNext() || iterable.iterator().hasNext();
            }

            public T next() {
                if (!this.c.hasNext()) {
                    java.util.Iterator<T> it = iterable.iterator();
                    this.c = it;
                    if (!it.hasNext()) {
                        throw new NoSuchElementException();
                    }
                }
                return this.c.next();
            }

            public void remove() {
                this.c.remove();
            }
        };
    }

    /* JADX WARNING: Removed duplicated region for block: B:2:0x0006  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean elementsEqual(java.util.Iterator<?> r3, java.util.Iterator<?> r4) {
        /*
        L_0x0000:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x001d
            boolean r0 = r4.hasNext()
            r1 = 0
            if (r0 != 0) goto L_0x000e
            return r1
        L_0x000e:
            java.lang.Object r0 = r3.next()
            java.lang.Object r2 = r4.next()
            boolean r0 = com.google.common.base.Objects.equal(r0, r2)
            if (r0 != 0) goto L_0x0000
            return r1
        L_0x001d:
            boolean r3 = r4.hasNext()
            r3 = r3 ^ 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.elementsEqual(java.util.Iterator, java.util.Iterator):boolean");
    }

    public static <T> UnmodifiableIterator<T> filter(final java.util.Iterator<T> it, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() {
            public final T computeNext() {
                T next;
                do {
                    java.util.Iterator it = it;
                    if (it.hasNext()) {
                        next = it.next();
                    } else {
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                } while (!predicate.apply(next));
                return next;
            }
        };
    }

    public static <T> T find(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return next;
            }
        }
        throw new NoSuchElementException();
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T... tArr) {
        boolean z;
        int length = tArr.length;
        if (length >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        Preconditions.checkPositionIndexes(0, length + 0, tArr.length);
        Preconditions.checkPositionIndex(0, length);
        if (length == 0) {
            return ArrayItr.i;
        }
        return new ArrayItr(tArr, length);
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            public T next() {
                return enumeration.nextElement();
            }
        };
    }

    public static int frequency(java.util.Iterator<?> it, Object obj) {
        int i = 0;
        while (contains(it, obj)) {
            i++;
        }
        return i;
    }

    public static <T> T get(java.util.Iterator<T> it, int i) {
        a(i);
        int advance = advance(it, i);
        if (it.hasNext()) {
            return it.next();
        }
        throw new IndexOutOfBoundsException("position (" + i + ") must be less than the number of elements that remained (" + advance + ")");
    }

    public static <T> T getLast(java.util.Iterator<T> it) {
        T next;
        do {
            next = it.next();
        } while (it.hasNext());
        return next;
    }

    public static <T> T getNext(java.util.Iterator<? extends T> it, T t) {
        return it.hasNext() ? it.next() : t;
    }

    public static <T> T getOnlyElement(java.util.Iterator<T> it) {
        T next = it.next();
        if (!it.hasNext()) {
            return next;
        }
        StringBuilder sb = new StringBuilder("expected one element but was: <");
        sb.append(next);
        for (int i = 0; i < 4 && it.hasNext(); i++) {
            sb.append(", ");
            sb.append(it.next());
        }
        if (it.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> int indexOf(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T> java.util.Iterator<T> limit(final java.util.Iterator<T> it, final int i) {
        boolean z;
        Preconditions.checkNotNull(it);
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "limit is negative");
        return new Object() {
            public int c;

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                return this.c < i && it.hasNext();
            }

            public T next() {
                if (hasNext()) {
                    this.c++;
                    return it.next();
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                it.remove();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends java.util.Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator(iterable, comparator);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(final java.util.Iterator<T> it, final int i) {
        boolean z;
        Preconditions.checkNotNull(it);
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        return new UnmodifiableIterator<List<Object>>(true) {
            public boolean hasNext() {
                return r2.hasNext();
            }

            public List<Object> next() {
                if (hasNext()) {
                    int i = r3;
                    Object[] objArr = new Object[i];
                    int i2 = 0;
                    while (i2 < i) {
                        java.util.Iterator it = r2;
                        if (!it.hasNext()) {
                            break;
                        }
                        objArr[i2] = it.next();
                        i2++;
                    }
                    for (int i3 = i2; i3 < i; i3++) {
                        objArr[i3] = null;
                    }
                    List<Object> unmodifiableList = Collections.unmodifiableList(Arrays.asList(objArr));
                    return (false || i2 == i) ? unmodifiableList : unmodifiableList.subList(0, i2);
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> UnmodifiableIterator<List<T>> partition(final java.util.Iterator<T> it, final int i) {
        boolean z;
        Preconditions.checkNotNull(it);
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        return new UnmodifiableIterator<List<Object>>(false) {
            public boolean hasNext() {
                return it.hasNext();
            }

            public List<Object> next() {
                if (hasNext()) {
                    int i = i;
                    Object[] objArr = new Object[i];
                    int i2 = 0;
                    while (i2 < i) {
                        java.util.Iterator it = it;
                        if (!it.hasNext()) {
                            break;
                        }
                        objArr[i2] = it.next();
                        i2++;
                    }
                    for (int i3 = i2; i3 < i; i3++) {
                        objArr[i3] = null;
                    }
                    List<Object> unmodifiableList = Collections.unmodifiableList(Arrays.asList(objArr));
                    return (false || i2 == i) ? unmodifiableList : unmodifiableList.subList(0, i2);
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(java.util.Iterator<? extends T> it) {
        if (it instanceof PeekingImpl) {
            return (PeekingImpl) it;
        }
        return new PeekingImpl(it);
    }

    public static boolean removeAll(java.util.Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static <T> boolean removeIf(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean z = false;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static boolean retainAll(java.util.Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean z = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(final T t) {
        return new UnmodifiableIterator<T>() {
            public boolean c;

            public boolean hasNext() {
                return !this.c;
            }

            public T next() {
                if (!this.c) {
                    this.c = true;
                    return t;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static int size(java.util.Iterator<?> it) {
        long j = 0;
        while (it.hasNext()) {
            it.next();
            j++;
        }
        return y3.b(j);
    }

    public static <T> T[] toArray(java.util.Iterator<? extends T> it, Class<T> cls) {
        return Iterables.toArray(Lists.newArrayList(it), cls);
    }

    public static String toString(java.util.Iterator<?> it) {
        StringBuilder sb = new StringBuilder("[");
        boolean z = true;
        while (it.hasNext()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(it.next());
            z = false;
        }
        sb.append(']');
        return sb.toString();
    }

    public static <F, T> java.util.Iterator<T> transform(java.util.Iterator<F> it, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(it) {
            public final T a(F f2) {
                return function.apply(f2);
            }
        };
    }

    public static <T> Optional<T> tryFind(java.util.Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return Optional.of(next);
            }
        }
        return Optional.absent();
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final java.util.Iterator<? extends T> it) {
        Preconditions.checkNotNull(it);
        if (it instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator) it;
        }
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return it.hasNext();
            }

            public T next() {
                return it.next();
            }
        };
    }

    @SafeVarargs
    public static <T> java.util.Iterator<T> cycle(T... tArr) {
        return cycle(Lists.newArrayList((E[]) tArr));
    }

    public static <T> T getLast(java.util.Iterator<? extends T> it, T t) {
        return it.hasNext() ? getLast(it) : t;
    }

    public static <T> UnmodifiableIterator<T> filter(java.util.Iterator<?> it, Class<T> cls) {
        return filter(it, Predicates.instanceOf(cls));
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return (PeekingIterator) Preconditions.checkNotNull(peekingIterator);
    }

    public static <T> java.util.Iterator<T> concat(java.util.Iterator<? extends T> it, java.util.Iterator<? extends T> it2, java.util.Iterator<? extends T> it3) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        final java.util.Iterator[] itArr = {it, it2, it3};
        return concat(new UnmodifiableIterator<Object>() {
            public int c = 0;

            public boolean hasNext() {
                return this.c < r4.length;
            }

            public Object next() {
                if (hasNext()) {
                    int i = this.c;
                    Object[] objArr = r4;
                    Object obj = objArr[i];
                    objArr[i] = null;
                    this.c = i + 1;
                    return obj;
                }
                throw new NoSuchElementException();
            }
        });
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static <T> T get(java.util.Iterator<? extends T> it, int i, T t) {
        a(i);
        advance(it, i);
        return getNext(it, t);
    }

    public static <T> T find(java.util.Iterator<? extends T> it, Predicate<? super T> predicate, T t) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(predicate);
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.apply(next)) {
                return next;
            }
        }
        return t;
    }

    public static <T> java.util.Iterator<T> concat(java.util.Iterator<? extends T> it, java.util.Iterator<? extends T> it2, java.util.Iterator<? extends T> it3, java.util.Iterator<? extends T> it4) {
        Preconditions.checkNotNull(it);
        Preconditions.checkNotNull(it2);
        Preconditions.checkNotNull(it3);
        Preconditions.checkNotNull(it4);
        final java.util.Iterator[] itArr = {it, it2, it3, it4};
        return concat(new UnmodifiableIterator<Object>() {
            public int c = 0;

            public boolean hasNext() {
                return this.c < r4.length;
            }

            public Object next() {
                if (hasNext()) {
                    int i = this.c;
                    Object[] objArr = r4;
                    Object obj = objArr[i];
                    objArr[i] = null;
                    this.c = i + 1;
                    return obj;
                }
                throw new NoSuchElementException();
            }
        });
    }

    public static <T> T getOnlyElement(java.util.Iterator<? extends T> it, T t) {
        return it.hasNext() ? getOnlyElement(it) : t;
    }

    public static <T> java.util.Iterator<T> concat(java.util.Iterator<? extends T>... itArr) {
        final java.util.Iterator[] itArr2 = (java.util.Iterator[]) Arrays.copyOf(itArr, itArr.length);
        for (java.util.Iterator checkNotNull : (java.util.Iterator[]) Preconditions.checkNotNull(itArr2)) {
            Preconditions.checkNotNull(checkNotNull);
        }
        return concat(new UnmodifiableIterator<Object>() {
            public int c = 0;

            public boolean hasNext() {
                return this.c < itArr2.length;
            }

            public Object next() {
                if (hasNext()) {
                    int i = this.c;
                    Object[] objArr = itArr2;
                    Object obj = objArr[i];
                    objArr[i] = null;
                    this.c = i + 1;
                    return obj;
                }
                throw new NoSuchElementException();
            }
        });
    }

    public static <T> java.util.Iterator<T> concat(java.util.Iterator<? extends java.util.Iterator<? extends T>> it) {
        return new ConcatenatedIterator(it);
    }
}
