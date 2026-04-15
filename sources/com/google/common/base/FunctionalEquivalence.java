package com.google.common.base;

import java.io.Serializable;

final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Function<F, ? extends T> c;
    public final Equivalence<T> f;

    public FunctionalEquivalence(Function<F, ? extends T> function, Equivalence<T> equivalence) {
        this.c = (Function) Preconditions.checkNotNull(function);
        this.f = (Equivalence) Preconditions.checkNotNull(equivalence);
    }

    public final boolean a(F f2, F f3) {
        Function<F, ? extends T> function = this.c;
        return this.f.equivalent(function.apply(f2), function.apply(f3));
    }

    public final int b(F f2) {
        return this.f.hash(this.c.apply(f2));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FunctionalEquivalence)) {
            return false;
        }
        FunctionalEquivalence functionalEquivalence = (FunctionalEquivalence) obj;
        if (!this.c.equals(functionalEquivalence.c) || !this.f.equals(functionalEquivalence.f)) {
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
