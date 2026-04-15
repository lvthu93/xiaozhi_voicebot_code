package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;

final class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Function<F, ? extends T> c;
    public final Ordering<T> f;

    public ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
        this.c = (Function) Preconditions.checkNotNull(function);
        this.f = (Ordering) Preconditions.checkNotNull(ordering);
    }

    public int compare(F f2, F f3) {
        Function<F, ? extends T> function = this.c;
        return this.f.compare(function.apply(f2), function.apply(f3));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ByFunctionOrdering)) {
            return false;
        }
        ByFunctionOrdering byFunctionOrdering = (ByFunctionOrdering) obj;
        if (!this.c.equals(byFunctionOrdering.c) || !this.f.equals(byFunctionOrdering.f)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.c, this.f);
    }

    public String toString() {
        return this.f + ".onResultOf(" + this.c + ")";
    }
}
