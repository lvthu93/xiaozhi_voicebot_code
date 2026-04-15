package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collection;

final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    public static final /* synthetic */ int k = 0;
    private static final long serialVersionUID = 0;
    public final Range<C> j;

    public static final class SerializedForm<C extends Comparable> implements Serializable {
        public final Range<C> c;
        public final DiscreteDomain<C> f;

        public SerializedForm() {
            throw null;
        }

        public SerializedForm(Range range, DiscreteDomain discreteDomain) {
            this.c = range;
            this.f = discreteDomain;
        }

        private Object readResolve() {
            return new RegularContiguousSet(this.c, this.f);
        }
    }

    public RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.j = range;
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return this.j.contains((Comparable) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> collection) {
        return Collections2.b(this, collection);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RegularContiguousSet) {
            RegularContiguousSet regularContiguousSet = (RegularContiguousSet) obj;
            if (this.i.equals(regularContiguousSet.i)) {
                if (!first().equals(regularContiguousSet.first()) || !last().equals(regularContiguousSet.last())) {
                    return false;
                }
                return true;
            }
        }
        return super.equals(obj);
    }

    public int hashCode() {
        return Sets.b(this);
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        Preconditions.checkNotNull(contiguousSet);
        DiscreteDomain<C> discreteDomain = contiguousSet.i;
        DiscreteDomain<C> discreteDomain2 = this.i;
        Preconditions.checkArgument(discreteDomain2.equals(discreteDomain));
        if (contiguousSet.isEmpty()) {
            return contiguousSet;
        }
        Comparable comparable = (Comparable) Ordering.natural().max(first(), contiguousSet.first());
        Comparable comparable2 = (Comparable) Ordering.natural().min(last(), contiguousSet.last());
        if (comparable.compareTo(comparable2) <= 0) {
            return ContiguousSet.create(Range.closed(comparable, comparable2), discreteDomain2);
        }
        return new EmptyContiguousSet(discreteDomain2);
    }

    public boolean isEmpty() {
        return false;
    }

    public final boolean isPartialView() {
        return false;
    }

    public final ImmutableList<C> k() {
        if (this.i.c) {
            return new ImmutableAsList<C>() {
                public final ImmutableCollection j() {
                    return RegularContiguousSet.this;
                }

                public C get(int i) {
                    Preconditions.checkElementIndex(i, size());
                    RegularContiguousSet regularContiguousSet = RegularContiguousSet.this;
                    return regularContiguousSet.i.a(regularContiguousSet.first(), (long) i);
                }
            };
        }
        return super.k();
    }

    public Range<C> range() {
        BoundType boundType = BoundType.CLOSED;
        return range(boundType, boundType);
    }

    public int size() {
        long distance = this.i.distance(first(), last());
        return distance >= 2147483647L ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : ((int) distance) + 1;
    }

    /* renamed from: t */
    public final ContiguousSet<C> q(C c, boolean z) {
        return w(Range.upTo(c, BoundType.b(z)));
    }

    /* renamed from: u */
    public final ContiguousSet<C> r(C c, boolean z, C c2, boolean z2) {
        if (c.compareTo(c2) != 0 || z || z2) {
            return w(Range.range(c, BoundType.b(z), c2, BoundType.b(z2)));
        }
        return new EmptyContiguousSet(this.i);
    }

    /* renamed from: v */
    public final ContiguousSet<C> s(C c, boolean z) {
        return w(Range.downTo(c, BoundType.b(z)));
    }

    public final ContiguousSet<C> w(Range<C> range) {
        Range<C> range2 = this.j;
        boolean isConnected = range2.isConnected(range);
        DiscreteDomain<C> discreteDomain = this.i;
        if (isConnected) {
            return ContiguousSet.create(range2.intersection(range), discreteDomain);
        }
        return new EmptyContiguousSet(discreteDomain);
    }

    public Object writeReplace() {
        return new SerializedForm(this.j, this.i);
    }

    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>(last()) {
            public final C f;

            {
                this.f = RegularContiguousSet.this.first();
            }

            /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Comparable a(java.lang.Object r3) {
                /*
                    r2 = this;
                    java.lang.Comparable r3 = (java.lang.Comparable) r3
                    int r0 = com.google.common.collect.RegularContiguousSet.k
                    C r0 = r2.f
                    if (r0 == 0) goto L_0x0012
                    com.google.common.collect.Range<java.lang.Comparable> r1 = com.google.common.collect.Range.g
                    int r0 = r3.compareTo(r0)
                    if (r0 != 0) goto L_0x0012
                    r0 = 1
                    goto L_0x0013
                L_0x0012:
                    r0 = 0
                L_0x0013:
                    if (r0 == 0) goto L_0x0017
                    r3 = 0
                    goto L_0x001f
                L_0x0017:
                    com.google.common.collect.RegularContiguousSet r0 = com.google.common.collect.RegularContiguousSet.this
                    com.google.common.collect.DiscreteDomain<C> r0 = r0.i
                    java.lang.Comparable r3 = r0.previous(r3)
                L_0x001f:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularContiguousSet.AnonymousClass2.a(java.lang.Object):java.lang.Comparable");
            }
        };
    }

    public C first() {
        return this.j.c.n(this.i);
    }

    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>(first()) {
            public final C f;

            {
                this.f = RegularContiguousSet.this.last();
            }

            /* JADX WARNING: Removed duplicated region for block: B:7:0x0015 A[RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0017  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Comparable a(java.lang.Object r3) {
                /*
                    r2 = this;
                    java.lang.Comparable r3 = (java.lang.Comparable) r3
                    int r0 = com.google.common.collect.RegularContiguousSet.k
                    C r0 = r2.f
                    if (r0 == 0) goto L_0x0012
                    com.google.common.collect.Range<java.lang.Comparable> r1 = com.google.common.collect.Range.g
                    int r0 = r3.compareTo(r0)
                    if (r0 != 0) goto L_0x0012
                    r0 = 1
                    goto L_0x0013
                L_0x0012:
                    r0 = 0
                L_0x0013:
                    if (r0 == 0) goto L_0x0017
                    r3 = 0
                    goto L_0x001f
                L_0x0017:
                    com.google.common.collect.RegularContiguousSet r0 = com.google.common.collect.RegularContiguousSet.this
                    com.google.common.collect.DiscreteDomain<C> r0 = r0.i
                    java.lang.Comparable r3 = r0.next(r3)
                L_0x001f:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularContiguousSet.AnonymousClass1.a(java.lang.Object):java.lang.Comparable");
            }
        };
    }

    public C last() {
        return this.j.f.i(this.i);
    }

    public Range<C> range(BoundType boundType, BoundType boundType2) {
        Range<C> range = this.j;
        Cut<C> cut = range.c;
        DiscreteDomain<C> discreteDomain = this.i;
        return new Range<>(cut.q(boundType, discreteDomain), range.f.r(boundType2, discreteDomain));
    }
}
