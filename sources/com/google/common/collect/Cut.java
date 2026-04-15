package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

abstract class Cut<C extends Comparable> implements Comparable<Cut<C>>, Serializable {
    private static final long serialVersionUID = 0;
    public final C c;

    /* renamed from: com.google.common.collect.Cut$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000c */
        static {
            /*
                com.google.common.collect.BoundType[] r0 = com.google.common.collect.BoundType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                r1 = 1
                r0[r1] = r1     // Catch:{ NoSuchFieldError -> 0x000c }
            L_0x000c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0012 }
                r1 = 2
                r2 = 0
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Cut.AnonymousClass1.<clinit>():void");
        }
    }

    public static final class AboveAll extends Cut<Comparable<?>> {
        public static final AboveAll f = new AboveAll();
        private static final long serialVersionUID = 0;

        public AboveAll() {
            super(null);
        }

        private Object readResolve() {
            return f;
        }

        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : 1;
        }

        public final void d(StringBuilder sb) {
            throw new AssertionError();
        }

        public final void e(StringBuilder sb) {
            sb.append("+∞)");
        }

        public final Comparable<?> h() {
            throw new IllegalStateException("range unbounded on this side");
        }

        public int hashCode() {
            return System.identityHashCode(this);
        }

        public final Comparable<?> i(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.maxValue();
        }

        public final boolean m(Comparable<?> comparable) {
            return false;
        }

        public final Comparable<?> n(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        public final BoundType o() {
            throw new AssertionError("this statement should be unreachable");
        }

        public final BoundType p() {
            throw new IllegalStateException();
        }

        public final Cut<Comparable<?>> q(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        public final Cut<Comparable<?>> r(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        public String toString() {
            return "+∞";
        }
    }

    public static final class AboveValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public AboveValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        public final Cut<C> b(DiscreteDomain<C> discreteDomain) {
            C next = discreteDomain.next(this.c);
            if (next != null) {
                return new BelowValue(next);
            }
            return AboveAll.f;
        }

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return Cut.super.compareTo((Cut) obj);
        }

        public final void d(StringBuilder sb) {
            sb.append('(');
            sb.append(this.c);
        }

        public final void e(StringBuilder sb) {
            sb.append(this.c);
            sb.append(']');
        }

        public int hashCode() {
            return ~this.c.hashCode();
        }

        public final C i(DiscreteDomain<C> discreteDomain) {
            return this.c;
        }

        public final boolean m(C c) {
            Range<Comparable> range = Range.g;
            if (this.c.compareTo(c) < 0) {
                return true;
            }
            return false;
        }

        public final C n(DiscreteDomain<C> discreteDomain) {
            return discreteDomain.next(this.c);
        }

        public final BoundType o() {
            return BoundType.OPEN;
        }

        public final BoundType p() {
            return BoundType.CLOSED;
        }

        public final Cut<C> q(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int ordinal = boundType.ordinal();
            if (ordinal == 0) {
                return this;
            }
            if (ordinal == 1) {
                C next = discreteDomain.next(this.c);
                if (next == null) {
                    return BelowAll.f;
                }
                return new BelowValue(next);
            }
            throw new AssertionError();
        }

        public final Cut<C> r(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int ordinal = boundType.ordinal();
            if (ordinal == 0) {
                C next = discreteDomain.next(this.c);
                if (next == null) {
                    return AboveAll.f;
                }
                return new BelowValue(next);
            } else if (ordinal == 1) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        public String toString() {
            return MqttTopic.TOPIC_LEVEL_SEPARATOR + this.c + "\\";
        }
    }

    public static final class BelowAll extends Cut<Comparable<?>> {
        public static final BelowAll f = new BelowAll();
        private static final long serialVersionUID = 0;

        public BelowAll() {
            super(null);
        }

        private Object readResolve() {
            return f;
        }

        public final Cut<Comparable<?>> b(DiscreteDomain<Comparable<?>> discreteDomain) {
            try {
                return new BelowValue(discreteDomain.minValue());
            } catch (NoSuchElementException unused) {
                return this;
            }
        }

        public int compareTo(Cut<Comparable<?>> cut) {
            return cut == this ? 0 : -1;
        }

        public final void d(StringBuilder sb) {
            sb.append("(-∞");
        }

        public final void e(StringBuilder sb) {
            throw new AssertionError();
        }

        public final Comparable<?> h() {
            throw new IllegalStateException("range unbounded on this side");
        }

        public int hashCode() {
            return System.identityHashCode(this);
        }

        public final Comparable<?> i(DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError();
        }

        public final boolean m(Comparable<?> comparable) {
            return true;
        }

        public final Comparable<?> n(DiscreteDomain<Comparable<?>> discreteDomain) {
            return discreteDomain.minValue();
        }

        public final BoundType o() {
            throw new IllegalStateException();
        }

        public final BoundType p() {
            throw new AssertionError("this statement should be unreachable");
        }

        public final Cut<Comparable<?>> q(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new IllegalStateException();
        }

        public final Cut<Comparable<?>> r(BoundType boundType, DiscreteDomain<Comparable<?>> discreteDomain) {
            throw new AssertionError("this statement should be unreachable");
        }

        public String toString() {
            return "-∞";
        }
    }

    public static final class BelowValue<C extends Comparable> extends Cut<C> {
        private static final long serialVersionUID = 0;

        public BelowValue(C c) {
            super((Comparable) Preconditions.checkNotNull(c));
        }

        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return Cut.super.compareTo((Cut) obj);
        }

        public final void d(StringBuilder sb) {
            sb.append('[');
            sb.append(this.c);
        }

        public final void e(StringBuilder sb) {
            sb.append(this.c);
            sb.append(')');
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        public final C i(DiscreteDomain<C> discreteDomain) {
            return discreteDomain.previous(this.c);
        }

        public final boolean m(C c) {
            Range<Comparable> range = Range.g;
            if (this.c.compareTo(c) <= 0) {
                return true;
            }
            return false;
        }

        public final C n(DiscreteDomain<C> discreteDomain) {
            return this.c;
        }

        public final BoundType o() {
            return BoundType.CLOSED;
        }

        public final BoundType p() {
            return BoundType.OPEN;
        }

        public final Cut<C> q(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int ordinal = boundType.ordinal();
            if (ordinal == 0) {
                C previous = discreteDomain.previous(this.c);
                if (previous == null) {
                    return BelowAll.f;
                }
                return new AboveValue(previous);
            } else if (ordinal == 1) {
                return this;
            } else {
                throw new AssertionError();
            }
        }

        public final Cut<C> r(BoundType boundType, DiscreteDomain<C> discreteDomain) {
            int ordinal = boundType.ordinal();
            if (ordinal == 0) {
                return this;
            }
            if (ordinal == 1) {
                C previous = discreteDomain.previous(this.c);
                if (previous == null) {
                    return AboveAll.f;
                }
                return new AboveValue(previous);
            }
            throw new AssertionError();
        }

        public String toString() {
            return "\\" + this.c + MqttTopic.TOPIC_LEVEL_SEPARATOR;
        }
    }

    public Cut(C c2) {
        this.c = c2;
    }

    public Cut<C> b(DiscreteDomain<C> discreteDomain) {
        return this;
    }

    public abstract void d(StringBuilder sb);

    public abstract void e(StringBuilder sb);

    public boolean equals(Object obj) {
        if (!(obj instanceof Cut)) {
            return false;
        }
        try {
            if (compareTo((Cut) obj) == 0) {
                return true;
            }
            return false;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public C h() {
        return this.c;
    }

    public abstract int hashCode();

    public abstract C i(DiscreteDomain<C> discreteDomain);

    public abstract boolean m(C c2);

    public abstract C n(DiscreteDomain<C> discreteDomain);

    public abstract BoundType o();

    public abstract BoundType p();

    public abstract Cut<C> q(BoundType boundType, DiscreteDomain<C> discreteDomain);

    public abstract Cut<C> r(BoundType boundType, DiscreteDomain<C> discreteDomain);

    public int compareTo(Cut<C> cut) {
        if (cut == BelowAll.f) {
            return 1;
        }
        if (cut == AboveAll.f) {
            return -1;
        }
        C c2 = cut.c;
        Range<Comparable> range = Range.g;
        int compareTo = this.c.compareTo(c2);
        if (compareTo != 0) {
            return compareTo;
        }
        boolean z = this instanceof AboveValue;
        if (z == (cut instanceof AboveValue)) {
            return 0;
        }
        if (z) {
            return 1;
        }
        return -1;
    }
}
