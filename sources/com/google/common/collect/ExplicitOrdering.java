package com.google.common.collect;

import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.List;

final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final ImmutableMap<T, Integer> c;

    public ExplicitOrdering() {
        throw null;
    }

    public ExplicitOrdering(List<T> list) {
        this.c = Maps.e(list);
    }

    public int compare(T t, T t2) {
        ImmutableMap<T, Integer> immutableMap = this.c;
        Integer num = immutableMap.get(t);
        if (num != null) {
            int intValue = num.intValue();
            Integer num2 = immutableMap.get(t2);
            if (num2 != null) {
                return intValue - num2.intValue();
            }
            throw new Ordering.IncomparableValueException(t2);
        }
        throw new Ordering.IncomparableValueException(t);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ExplicitOrdering) {
            return this.c.equals(((ExplicitOrdering) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public String toString() {
        return "Ordering.explicit(" + this.c.keySet() + ")";
    }
}
