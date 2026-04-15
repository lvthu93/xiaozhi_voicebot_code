package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Cut;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public final class Range<C extends Comparable> extends RangeGwtSerializationDependencies implements Predicate<C> {
    public static final Range<Comparable> g = new Range<>(Cut.BelowAll.f, Cut.AboveAll.f);
    private static final long serialVersionUID = 0;
    public final Cut<C> c;
    public final Cut<C> f;

    /* renamed from: com.google.common.collect.Range$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000d */
        static {
            /*
                com.google.common.collect.BoundType[] r0 = com.google.common.collect.BoundType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                r1 = 1
                r2 = 0
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x000d }
            L_0x000d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Range.AnonymousClass1.<clinit>():void");
        }
    }

    public static class LowerBoundFn implements Function<Range, Cut> {
        public static final LowerBoundFn c = new LowerBoundFn();

        public Cut apply(Range range) {
            return range.c;
        }
    }

    public static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
        public static final Ordering<Range<?>> c = new RangeLexOrdering();
        private static final long serialVersionUID = 0;

        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare((Comparable<?>) range.c, (Comparable<?>) range2.c).compare((Comparable<?>) range.f, (Comparable<?>) range2.f).result();
        }
    }

    public static class UpperBoundFn implements Function<Range, Cut> {
        public static final UpperBoundFn c = new UpperBoundFn();

        public Cut apply(Range range) {
            return range.f;
        }
    }

    public Range(Cut<C> cut, Cut<C> cut2) {
        this.c = (Cut) Preconditions.checkNotNull(cut);
        this.f = (Cut) Preconditions.checkNotNull(cut2);
        if (cut.compareTo(cut2) > 0 || cut == Cut.AboveAll.f || cut2 == Cut.BelowAll.f) {
            StringBuilder sb = new StringBuilder("Invalid range: ");
            StringBuilder sb2 = new StringBuilder(16);
            cut.d(sb2);
            sb2.append("..");
            cut2.e(sb2);
            sb.append(sb2.toString());
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return g;
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c2) {
        return new Range<>(new Cut.BelowValue(c2), Cut.AboveAll.f);
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c2) {
        return new Range<>(Cut.BelowAll.f, new Cut.AboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> closed(C c2, C c3) {
        return new Range<>(new Cut.BelowValue(c2), new Cut.AboveValue(c3));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c2, C c3) {
        return new Range<>(new Cut.BelowValue(c2), new Cut.BelowValue(c3));
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c2, BoundType boundType) {
        int ordinal = boundType.ordinal();
        if (ordinal == 0) {
            return greaterThan(c2);
        }
        if (ordinal == 1) {
            return atLeast(c2);
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) iterable;
            Comparator comparator = sortedSet.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                return closed((Comparable) sortedSet.first(), (Comparable) sortedSet.last());
            }
        }
        Iterator<C> it = iterable.iterator();
        Comparable comparable = (Comparable) Preconditions.checkNotNull(it.next());
        Comparable comparable2 = comparable;
        while (it.hasNext()) {
            Comparable comparable3 = (Comparable) Preconditions.checkNotNull(it.next());
            comparable = (Comparable) Ordering.natural().min(comparable, comparable3);
            comparable2 = (Comparable) Ordering.natural().max(comparable2, comparable3);
        }
        return closed(comparable, comparable2);
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c2) {
        return new Range<>(new Cut.AboveValue(c2), Cut.AboveAll.f);
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c2) {
        return new Range<>(Cut.BelowAll.f, new Cut.BelowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> open(C c2, C c3) {
        return new Range<>(new Cut.AboveValue(c2), new Cut.BelowValue(c3));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c2, C c3) {
        return new Range<>(new Cut.AboveValue(c2), new Cut.AboveValue(c3));
    }

    public static <C extends Comparable<?>> Range<C> range(C c2, BoundType boundType, C c3, BoundType boundType2) {
        Cut cut;
        Cut cut2;
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        BoundType boundType3 = BoundType.OPEN;
        if (boundType == boundType3) {
            cut = new Cut.AboveValue(c2);
        } else {
            cut = new Cut.BelowValue(c2);
        }
        if (boundType2 == boundType3) {
            cut2 = new Cut.BelowValue(c3);
        } else {
            cut2 = new Cut.AboveValue(c3);
        }
        return new Range<>(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c2) {
        return closed(c2, c2);
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c2, BoundType boundType) {
        int ordinal = boundType.ordinal();
        if (ordinal == 0) {
            return lessThan(c2);
        }
        if (ordinal == 1) {
            return atMost(c2);
        }
        throw new AssertionError();
    }

    public Range<C> canonical(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        Cut<C> cut = this.c;
        Cut<C> b = cut.b(discreteDomain);
        Cut<C> cut2 = this.f;
        Cut<C> b2 = cut2.b(discreteDomain);
        if (b == cut && b2 == cut2) {
            return this;
        }
        return new Range<>(b, b2);
    }

    public boolean contains(C c2) {
        Preconditions.checkNotNull(c2);
        if (!this.c.m(c2) || this.f.m(c2)) {
            return false;
        }
        return true;
    }

    public boolean containsAll(Iterable<? extends C> iterable) {
        if (Iterables.isEmpty(iterable)) {
            return true;
        }
        if (iterable instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) iterable;
            Comparator comparator = sortedSet.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                if (!contains((Comparable) sortedSet.first()) || !contains((Comparable) sortedSet.last())) {
                    return false;
                }
                return true;
            }
        }
        Iterator<? extends C> it = iterable.iterator();
        while (it.hasNext()) {
            if (!contains((Comparable) it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> range) {
        if (this.c.compareTo(range.c) > 0 || this.f.compareTo(range.f) < 0) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Range)) {
            return false;
        }
        Range range = (Range) obj;
        if (!this.c.equals(range.c) || !this.f.equals(range.f)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.common.collect.Range<C>, code=com.google.common.collect.Range, for r3v0, types: [com.google.common.collect.Range, com.google.common.collect.Range<C>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.common.collect.Range<C> gap(com.google.common.collect.Range r3) {
        /*
            r2 = this;
            com.google.common.collect.Cut<C> r0 = r3.c
            com.google.common.collect.Cut<C> r1 = r2.c
            int r0 = r1.compareTo(r0)
            if (r0 >= 0) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            if (r0 == 0) goto L_0x0011
            r1 = r2
            goto L_0x0012
        L_0x0011:
            r1 = r3
        L_0x0012:
            if (r0 == 0) goto L_0x0015
            goto L_0x0016
        L_0x0015:
            r3 = r2
        L_0x0016:
            com.google.common.collect.Range r0 = new com.google.common.collect.Range
            com.google.common.collect.Cut<C> r1 = r1.f
            com.google.common.collect.Cut<C> r3 = r3.c
            r0.<init>(r1, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Range.gap(com.google.common.collect.Range):com.google.common.collect.Range");
    }

    public boolean hasLowerBound() {
        if (this.c != Cut.BelowAll.f) {
            return true;
        }
        return false;
    }

    public boolean hasUpperBound() {
        if (this.f != Cut.AboveAll.f) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.c.hashCode() * 31) + this.f.hashCode();
    }

    public Range<C> intersection(Range<C> range) {
        Cut<C> cut = range.c;
        Cut<C> cut2 = this.c;
        int compareTo = cut2.compareTo(cut);
        Cut<C> cut3 = this.f;
        Cut<C> cut4 = range.f;
        int compareTo2 = cut3.compareTo(cut4);
        if (compareTo >= 0 && compareTo2 <= 0) {
            return this;
        }
        if (compareTo <= 0 && compareTo2 >= 0) {
            return range;
        }
        if (compareTo < 0) {
            cut2 = range.c;
        }
        if (compareTo2 > 0) {
            cut3 = cut4;
        }
        return new Range<>(cut2, cut3);
    }

    public boolean isConnected(Range<C> range) {
        if (this.c.compareTo(range.f) > 0 || range.c.compareTo(this.f) > 0) {
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return this.c.equals(this.f);
    }

    public BoundType lowerBoundType() {
        return this.c.o();
    }

    public C lowerEndpoint() {
        return this.c.h();
    }

    public Object readResolve() {
        if (equals(g)) {
            return all();
        }
        return this;
    }

    public Range<C> span(Range<C> range) {
        Cut<C> cut = range.c;
        Cut<C> cut2 = this.c;
        int compareTo = cut2.compareTo(cut);
        Cut<C> cut3 = this.f;
        Cut<C> cut4 = range.f;
        int compareTo2 = cut3.compareTo(cut4);
        if (compareTo <= 0 && compareTo2 >= 0) {
            return this;
        }
        if (compareTo >= 0 && compareTo2 <= 0) {
            return range;
        }
        if (compareTo > 0) {
            cut2 = range.c;
        }
        if (compareTo2 < 0) {
            cut3 = cut4;
        }
        return new Range<>(cut2, cut3);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(16);
        this.c.d(sb);
        sb.append("..");
        this.f.e(sb);
        return sb.toString();
    }

    public BoundType upperBoundType() {
        return this.f.p();
    }

    public C upperEndpoint() {
        return this.f.h();
    }

    @Deprecated
    public boolean apply(C c2) {
        return contains(c2);
    }
}
