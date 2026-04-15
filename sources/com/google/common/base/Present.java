package com.google.common.base;

import java.util.Collections;
import java.util.Set;

final class Present<T> extends Optional<T> {
    private static final long serialVersionUID = 0;
    public final T c;

    public Present(T t) {
        this.c = t;
    }

    public Set<T> asSet() {
        return Collections.singleton(this.c);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Present) {
            return this.c.equals(((Present) obj).c);
        }
        return false;
    }

    public T get() {
        return this.c;
    }

    public int hashCode() {
        return this.c.hashCode() + 1502476572;
    }

    public boolean isPresent() {
        return true;
    }

    public T or(T t) {
        Preconditions.checkNotNull(t, "use Optional.orNull() instead of Optional.or(null)");
        return this.c;
    }

    public T orNull() {
        return this.c;
    }

    public String toString() {
        return "Optional.of(" + this.c + ")";
    }

    public <V> Optional<V> transform(Function<? super T, V> function) {
        return new Present(Preconditions.checkNotNull(function.apply(this.c), "the Function passed to Optional.transform() must not return null."));
    }

    public Optional<T> or(Optional<? extends T> optional) {
        Preconditions.checkNotNull(optional);
        return this;
    }

    public T or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return this.c;
    }
}
