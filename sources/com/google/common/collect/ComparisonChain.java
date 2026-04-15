package com.google.common.collect;

import java.util.Comparator;

public abstract class ComparisonChain {
    public static final ComparisonChain a = new ComparisonChain() {
        public static ComparisonChain a(int i) {
            if (i < 0) {
                return ComparisonChain.b;
            }
            if (i > 0) {
                return ComparisonChain.c;
            }
            return ComparisonChain.a;
        }

        public ComparisonChain compare(Comparable comparable, Comparable comparable2) {
            return a(comparable.compareTo(comparable2));
        }

        public ComparisonChain compareFalseFirst(boolean z, boolean z2) {
            return a(z == z2 ? 0 : z ? 1 : -1);
        }

        public ComparisonChain compareTrueFirst(boolean z, boolean z2) {
            return a(z2 == z ? 0 : z2 ? 1 : -1);
        }

        public int result() {
            return 0;
        }

        public <T> ComparisonChain compare(T t, T t2, Comparator<T> comparator) {
            return a(comparator.compare(t, t2));
        }

        public ComparisonChain compare(int i, int i2) {
            return a(i < i2 ? -1 : i > i2 ? 1 : 0);
        }

        public ComparisonChain compare(long j, long j2) {
            int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
            return a(i < 0 ? -1 : i > 0 ? 1 : 0);
        }

        public ComparisonChain compare(float f, float f2) {
            return a(Float.compare(f, f2));
        }

        public ComparisonChain compare(double d, double d2) {
            return a(Double.compare(d, d2));
        }
    };
    public static final ComparisonChain b = new InactiveComparisonChain(-1);
    public static final ComparisonChain c = new InactiveComparisonChain(1);

    public static final class InactiveComparisonChain extends ComparisonChain {
        public final int d;

        public InactiveComparisonChain(int i) {
            this.d = i;
        }

        public ComparisonChain compare(double d2, double d3) {
            return this;
        }

        public ComparisonChain compare(float f, float f2) {
            return this;
        }

        public ComparisonChain compare(int i, int i2) {
            return this;
        }

        public ComparisonChain compare(long j, long j2) {
            return this;
        }

        public ComparisonChain compare(Comparable comparable, Comparable comparable2) {
            return this;
        }

        public <T> ComparisonChain compare(T t, T t2, Comparator<T> comparator) {
            return this;
        }

        public ComparisonChain compareFalseFirst(boolean z, boolean z2) {
            return this;
        }

        public ComparisonChain compareTrueFirst(boolean z, boolean z2) {
            return this;
        }

        public int result() {
            return this.d;
        }
    }

    public static ComparisonChain start() {
        return a;
    }

    public abstract ComparisonChain compare(double d, double d2);

    public abstract ComparisonChain compare(float f, float f2);

    public abstract ComparisonChain compare(int i, int i2);

    public abstract ComparisonChain compare(long j, long j2);

    @Deprecated
    public final ComparisonChain compare(Boolean bool, Boolean bool2) {
        return compareFalseFirst(bool.booleanValue(), bool2.booleanValue());
    }

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2);

    public abstract <T> ComparisonChain compare(T t, T t2, Comparator<T> comparator);

    public abstract ComparisonChain compareFalseFirst(boolean z, boolean z2);

    public abstract ComparisonChain compareTrueFirst(boolean z, boolean z2);

    public abstract int result();
}
