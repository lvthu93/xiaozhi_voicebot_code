package com.google.common.collect;

abstract class IndexedImmutableSet<E> extends ImmutableSet<E> {
    public final int a(int i, Object[] objArr) {
        return asList().a(i, objArr);
    }

    public abstract E get(int i);

    public final ImmutableList<E> k() {
        return new ImmutableList<E>() {
            public E get(int i) {
                return IndexedImmutableSet.this.get(i);
            }

            public final boolean isPartialView() {
                return IndexedImmutableSet.this.isPartialView();
            }

            public int size() {
                return IndexedImmutableSet.this.size();
            }
        };
    }

    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }
}
