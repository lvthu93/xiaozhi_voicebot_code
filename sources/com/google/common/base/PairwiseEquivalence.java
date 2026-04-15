package com.google.common.base;

import java.io.Serializable;
import java.util.Iterator;

final class PairwiseEquivalence<T> extends Equivalence<Iterable<T>> implements Serializable {
    private static final long serialVersionUID = 1;
    public final Equivalence<? super T> c;

    public PairwiseEquivalence(Equivalence<? super T> equivalence) {
        this.c = (Equivalence) Preconditions.checkNotNull(equivalence);
    }

    public final boolean a(Object obj, Object obj2) {
        Iterator it = ((Iterable) obj).iterator();
        Iterator it2 = ((Iterable) obj2).iterator();
        while (it.hasNext() && it2.hasNext()) {
            if (!this.c.equivalent(it.next(), it2.next())) {
                return false;
            }
        }
        if (it.hasNext() || it2.hasNext()) {
            return false;
        }
        return true;
    }

    public final int b(Object obj) {
        int i = 78721;
        for (Object hash : (Iterable) obj) {
            i = (i * 24943) + this.c.hash(hash);
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PairwiseEquivalence) {
            return this.c.equals(((PairwiseEquivalence) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode() ^ 1185147655;
    }

    public String toString() {
        return this.c + ".pairwise()";
    }
}
