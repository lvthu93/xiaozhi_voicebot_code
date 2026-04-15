package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;

final class GeneralRange<T> implements Serializable {
    public final Comparator<? super T> c;
    public final boolean f;
    public final T g;
    public final BoundType h;
    public final boolean i;
    public final T j;
    public final BoundType k;

    public GeneralRange(Comparator<? super T> comparator, boolean z, T t, BoundType boundType, boolean z2, T t2, BoundType boundType2) {
        boolean z3;
        boolean z4;
        this.c = (Comparator) Preconditions.checkNotNull(comparator);
        this.f = z;
        this.i = z2;
        this.g = t;
        this.h = (BoundType) Preconditions.checkNotNull(boundType);
        this.j = t2;
        this.k = (BoundType) Preconditions.checkNotNull(boundType2);
        if (z) {
            comparator.compare(t, t);
        }
        if (z2) {
            comparator.compare(t2, t2);
        }
        if (z && z2) {
            int compare = comparator.compare(t, t2);
            boolean z5 = true;
            if (compare <= 0) {
                z3 = true;
            } else {
                z3 = false;
            }
            Preconditions.checkArgument(z3, "lowerEndpoint (%s) > upperEndpoint (%s)", (Object) t, (Object) t2);
            if (compare == 0) {
                BoundType boundType3 = BoundType.OPEN;
                if (boundType != boundType3) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                Preconditions.checkArgument(z4 | (boundType2 == boundType3 ? false : z5));
            }
        }
    }

    public final boolean a(T t) {
        return !d(t) && !c(t);
    }

    public final GeneralRange<T> b(GeneralRange<T> generalRange) {
        boolean z;
        T t;
        boolean z2;
        BoundType boundType;
        BoundType boundType2;
        T t2;
        int compare;
        int compare2;
        int compare3;
        GeneralRange<T> generalRange2 = generalRange;
        Preconditions.checkNotNull(generalRange);
        Comparator<? super T> comparator = this.c;
        Preconditions.checkArgument(comparator.equals(generalRange2.c));
        BoundType boundType3 = BoundType.OPEN;
        boolean z3 = generalRange2.f;
        BoundType boundType4 = generalRange2.h;
        T t3 = generalRange2.g;
        boolean z4 = this.f;
        if (!z4) {
            z = z3;
        } else {
            T t4 = this.g;
            if (!z3 || ((compare3 = comparator.compare(t4, t3)) >= 0 && !(compare3 == 0 && boundType4 == boundType3))) {
                boundType4 = this.h;
                z = z4;
                t3 = t4;
            } else {
                z = z4;
            }
        }
        boolean z5 = generalRange2.i;
        BoundType boundType5 = generalRange2.k;
        T t5 = generalRange2.j;
        boolean z6 = this.i;
        if (!z6) {
            t = t5;
            z2 = z5;
        } else {
            T t6 = this.j;
            if (!z5 || ((compare2 = comparator.compare(t6, t5)) <= 0 && !(compare2 == 0 && boundType5 == boundType3))) {
                boundType5 = this.k;
                z2 = z6;
                t = t6;
            } else {
                t = t5;
                z2 = z6;
            }
        }
        if (!z || !z2 || ((compare = comparator.compare(t3, t)) <= 0 && !(compare == 0 && boundType4 == boundType3 && boundType5 == boundType3))) {
            boundType2 = boundType4;
            t2 = t3;
            boundType = boundType5;
        } else {
            boundType = BoundType.CLOSED;
            boundType2 = boundType3;
            t2 = t;
        }
        return new GeneralRange(this.c, z, t2, boundType2, z2, t, boundType);
    }

    public final boolean c(T t) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (!this.i) {
            return false;
        }
        int compare = this.c.compare(t, this.j);
        if (compare > 0) {
            z = true;
        } else {
            z = false;
        }
        if (compare == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.k == BoundType.OPEN) {
            z3 = true;
        }
        return (z2 & z3) | z;
    }

    public final boolean d(T t) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (!this.f) {
            return false;
        }
        int compare = this.c.compare(t, this.g);
        if (compare < 0) {
            z = true;
        } else {
            z = false;
        }
        if (compare == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.h == BoundType.OPEN) {
            z3 = true;
        }
        return (z2 & z3) | z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GeneralRange)) {
            return false;
        }
        GeneralRange generalRange = (GeneralRange) obj;
        if (!this.c.equals(generalRange.c) || this.f != generalRange.f || this.i != generalRange.i || !this.h.equals(generalRange.h) || !this.k.equals(generalRange.k) || !Objects.equal(this.g, generalRange.g) || !Objects.equal(this.j, generalRange.j)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.c, this.g, this.h, this.j, this.k);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(":");
        BoundType boundType = BoundType.CLOSED;
        sb.append(this.h == boundType ? '[' : '(');
        sb.append(this.f ? this.g : "-∞");
        sb.append(',');
        sb.append(this.i ? this.j : "∞");
        sb.append(this.k == boundType ? ']' : ')');
        return sb.toString();
    }
}
