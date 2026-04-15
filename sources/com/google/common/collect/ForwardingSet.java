package com.google.common.collect;

import java.util.Set;

public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E> {
    public boolean equals(Object obj) {
        return obj == this || delegate().equals(obj);
    }

    /* renamed from: g */
    public abstract Set<E> delegate();

    public int hashCode() {
        return delegate().hashCode();
    }
}
