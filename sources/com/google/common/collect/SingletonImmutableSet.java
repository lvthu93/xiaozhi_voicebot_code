package com.google.common.collect;

import com.google.common.base.Preconditions;

final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    public final transient E g;
    public transient int h;

    public SingletonImmutableSet(E e) {
        this.g = Preconditions.checkNotNull(e);
    }

    public final int a(int i, Object[] objArr) {
        objArr[i] = this.g;
        return i + 1;
    }

    public boolean contains(Object obj) {
        return this.g.equals(obj);
    }

    public final int hashCode() {
        int i = this.h;
        if (i != 0) {
            return i;
        }
        int hashCode = this.g.hashCode();
        this.h = hashCode;
        return hashCode;
    }

    public final boolean isPartialView() {
        return false;
    }

    public final ImmutableList<E> k() {
        return ImmutableList.of(this.g);
    }

    public final boolean l() {
        return this.h != 0;
    }

    public int size() {
        return 1;
    }

    public String toString() {
        return "[" + this.g.toString() + ']';
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.g);
    }

    public SingletonImmutableSet(int i, Object obj) {
        this.g = obj;
        this.h = i;
    }
}
